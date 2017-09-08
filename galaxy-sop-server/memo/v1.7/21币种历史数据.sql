/**
 * 默认为人民币
 */
UPDATE information_listdata SET field_6='2243' WHERE id IN (SELECT id FROM (SELECT id FROM information_listdata WHERE title_id = 1908 AND (field_6 IS NULL OR field_6 = '')) AS b);
