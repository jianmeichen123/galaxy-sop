	/**
 * @version v0.1 (01/2014)
 *
 * Authors:
 *   blue
 *
 * usage example : 
 *	1:$('#div1 textarea').textareaScroll();
*/


/*begin mousewheel*/
(function($) {

	var types = ['DOMMouseScroll', 'mousewheel'];

	if ($.event.fixHooks) {
	    for ( var i=types.length; i; ) {
	        $.event.fixHooks[ types[--i] ] = $.event.mouseHooks;
	    }
	}

	$.event.special.mousewheel = {
	    setup: function() {
	        if ( this.addEventListener ) {
	            for ( var i=types.length; i; ) {
	                this.addEventListener( types[--i], handler, false );
	            }
	        } else {
	            this.onmousewheel = handler;
	        }
	    },
	    
	    teardown: function() {
	        if ( this.removeEventListener ) {
	            for ( var i=types.length; i; ) {
	                this.removeEventListener( types[--i], handler, false );
	            }
	        } else {
	            this.onmousewheel = null;
	        }
	    }
	};

	$.fn.extend({
	    mousewheel: function(fn) {
	        return fn ? this.bind("mousewheel", fn) : this.trigger("mousewheel");
	    },
	    
	    unmousewheel: function(fn) {
	        return this.unbind("mousewheel", fn);
	    }
	});


	function handler(event) {
	    var orgEvent = event || window.event, args = [].slice.call( arguments, 1 ), delta = 0, returnValue = true, deltaX = 0, deltaY = 0;
	    event = $.event.fix(orgEvent);
	    event.type = "mousewheel";
	    
	    // Old school scrollwheel delta
	    if ( orgEvent.wheelDelta ) { delta = orgEvent.wheelDelta/120; }
	    if ( orgEvent.detail     ) { delta = -orgEvent.detail/3; }
	    
	    // New school multidimensional scroll (touchpads) deltas
	    deltaY = delta;
	    
	    // Gecko
	    if ( orgEvent.axis !== undefined && orgEvent.axis === orgEvent.HORIZONTAL_AXIS ) {
	        deltaY = 0;
	        deltaX = -1*delta;
	    }
	    
	    // Webkit
	    if ( orgEvent.wheelDeltaY !== undefined ) { deltaY = orgEvent.wheelDeltaY/120; }
	    if ( orgEvent.wheelDeltaX !== undefined ) { deltaX = -1*orgEvent.wheelDeltaX/120; }
	    
	    // Add event and delta to the front of the arguments
	    args.unshift(event, delta, deltaX, deltaY);
	    
	    return ($.event.dispatch || $.event.handle).apply(this, args);
	}
})(jQuery);
/*end mousewheel*/

(function($) {

    $.fn.textareaScroll = function(opts) {
        // Initialize textarea-scrobar for all elements in jQuery collection
        this.each(function() {
            init.call(this, opts);
        });

        return this;
    };

    function init(opts) {
        var $textarea     = $(this),
            scrollContent = '<div class="mTEXTAREA_scrollTools">'+
								'<div class="mTEXTAREA_draggerContainer">'+
									'<div class="mTEXTAREA_draggerRail"></div>'+
									'<div class="mTEXTAREA_dragger" oncontextmenu="return false;">'+
										'<div class="mTEXTAREA_dragger_bar" ></div>'+
									'</div>'+
								'</div>'+
							'</div>',
            heightTextare = 0,
            scrollHeight  = 0,
            mousePosition = {iX : 0},
            options       = $.extend({
            	outline   : false,
            	resize    : false,
                mouseup   : $.noop, 
                mousedown : $.noop,
                enter     : $.noop,
                exit      : $.noop,
                onload    : $.noop
            }, opts);
        
   
        var addStyle = function($dom,addStyleObject) {
        	var sStyle      = $dom.attr('style'),
        		aStyle      = sStyle ? sStyle.split(';') : [],
                oStyle      = {},
                aFinalstyle = [];
        	for (var i = 0; i < aStyle.length; i++) {
        		var sPerStyle = aStyle[i];
        		if(sPerStyle) {
        			var sAttr = $.trim(sPerStyle.split(':')[0]);
        			if(typeof(addStyleObject[sAttr]) != 'undefined') {
                        oStyle[sAttr] = addStyleObject[sAttr];
                    }
        		}
        	}
            var oNewStyle = $.extend(addStyleObject,oStyle);
            for (var i in oNewStyle) {
                if (oNewStyle.hasOwnProperty(i)) {
                    aFinalstyle.push(i+':'+oNewStyle[i]+';');
                }
            }
        	$dom.attr('style',aFinalstyle.join(''));
        };
        
        /**
		 *  give the textarea scrobar	
         */
        var showScrobar = function() {
        	if($textarea.parent().find('.mTEXTAREA_scrollTools').length == 0) {
        		$(scrollContent).insertAfter($textarea[0]);
        	}
            getDraggerBarHeight();
        };

        /**
         *  get dragger bar height
         */
        var getDraggerBarHeight = function() {
            var heightReally      = $textarea[0].scrollHeight,
                heightT           = heightTextare === 0 ? $textarea.outerWidth() : heightTextare,
                srcollbarHeight   = heightT/heightReally * heightT;
                $mTEXTAREADragger = $textarea.parent().find('.mTEXTAREA_dragger');
            $mTEXTAREADragger.height(parseFloat(srcollbarHeight));
            setTopandScroll();
            $mTEXTAREADragger = null;
        }

        var resetTextarea = function() {
            mousePosition = {iX : 0},
            $textarea.parent().find('.mTEXTAREA_scrollTools').remove();
        }

		/**
		 *  if container in textarea overflow? true: addScrobar; false: deleteScrobar	
         */
        var checkIsScrobar = function(e) {
        	e.preventDefault();
        	var textareaHeight = $textarea.height(),
        		heightReally   = parseFloat($textarea[0].scrollHeight);
        		paddingTop     = parseFloat($textarea.css('padding-top').replace('px','')),
        		paddingBottom  = parseFloat($textarea.css('padding-bottom').replace('px',''));
        	if(navigator.userAgent.indexOf("Chrome") > 0) { //for chrome scrollHeight 
		    	isNaN(paddingTop) ? '' : heightReally = heightReally - paddingTop;
		    	isNaN(paddingBottom) ? '' : heightReally = heightReally - paddingBottom;
		   	}
        	if(heightReally > textareaHeight && scrollHeight !== heightReally) { //show scrollbar
        		showScrobar();
                scrollHeight = heightReally;
        	}
        	else if(heightReally < textareaHeight || heightReally == textareaHeight) {//hide scrollbar
                resetTextarea();
            }

        };

        var checkTop = function(rTop) {
            var maxTop = heightTextare - $textarea.parent().find('.mTEXTAREA_dragger').height();
            (rTop < 0) && (rTop = 0);
            (rTop > maxTop) && (rTop = maxTop);
            return rTop;
        }

        var setTopandScroll = function(rTop) {
            var $elem        = $textarea.parent()
                heightReally = $textarea[0].scrollHeight,
                heightT      = heightTextare === 0 ? $textarea.outerWidth() : heightTextare;
            if(typeof(rTop) != 'undefined') {
                $elem.find('.mTEXTAREA_dragger').css('top',rTop);
                if(navigator.userAgent.indexOf("Firefox") > 0) { //for Firefox 
                    $textarea.scrollTop(parseFloat(heightReally/heightT*rTop)+3);
                }
                else {
                   $textarea.scrollTop(parseFloat(heightReally/heightT*rTop)); 
                }
                
            }
            else {
                var scrollTop = $textarea.scrollTop();
                rTop = parseFloat(scrollTop/(heightReally/heightT));
                rTop = checkTop(rTop)
                $elem.find('.mTEXTAREA_dragger').css('top',rTop);
            }
            $elem = null;
        }
        var mouseMove = function(e) {
            e.preventDefault();
            var iX    = mousePosition.iX,
                $elem = $textarea.parent();
                moveX = e.clientY,
                iTop  = $elem.find('.mTEXTAREA_dragger').position().top,
                rTop  = iTop;
            if(iX !== 0) {
                rTop = iTop + (moveX-iX);
            }
            rTop = checkTop(rTop);
            mousePosition.iX = moveX;

            setTopandScroll(rTop);
            $elem = null;
        }
        var clearDragger =function(e) {
            e.preventDefault();
            $(document).unbind('mousemove');
        }
        /*
         *  bind events for dragger bar
         */
        var textareaDragger = function(e) {
            e.preventDefault();
            var type = e.type;
            if(type == 'mousedown') {
                $(document)
                    .bind('mousemove',mouseMove)
                    .bind('mouseup',clearDragger);
            }
            else {
                $(document).unbind('mousemove');
            }
        };
        var mousewheelDragger = function(e,x) {
            e.preventDefault();
            var $parent = $textarea.parent();
            if($parent.find('.mTEXTAREA_dragger').length > 0) {
                var rTop = $parent.find('.mTEXTAREA_dragger').position().top;
                rTop = rTop - x;
                rTop = checkTop(rTop);
                setTopandScroll(rTop);
            } 
            $parent = null;
        }

        var putInContainer = function() {
        	var iWidth        = $textarea.outerWidth()+4,
        		iHeight       = $textarea.outerHeight(),
        		textAreaStyle = {'margin': 0,'padding-right':'6px'};

            heightTextare = iHeight;
        	if(!options.resize) {
        		textAreaStyle['resize'] = 'none';
        	}
        	if(!options.outline) {
        		textAreaStyle['outline'] = 'none';
        	}
        	addStyle($textarea,textAreaStyle);
        	$textarea.wrapAll('<div class ="scrobar_textarea" style="width:'+iWidth+'px;height:'+iHeight+'px;"></div>');
        	options.onload();
        };
        $textarea.val('');//for firefox
        putInContainer();
        $textarea.keyup(checkIsScrobar);
        $textarea.mousewheel(mousewheelDragger);
        $textarea.parent().undelegate().delegate('.mTEXTAREA_dragger','mousedown mouseup',textareaDragger);

    };
})(jQuery);