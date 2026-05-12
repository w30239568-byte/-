-- signIn 双重幂等：请求唯一键
ALTER TABLE la_activity_user_task_record
    ADD COLUMN request_key VARCHAR(64) NULL COMMENT '请求防重唯一键' AFTER is_open;

ALTER TABLE la_activity_user_task_record
    ADD UNIQUE KEY uk_signin_user_activity_req (user_id, activity_id, request_key);
