CREATE TABLE IF NOT EXISTS `user_has_role` (
    `user_id` BIGINT NOT NULL ,
    `role_id` BIGINT NOT NULL,
    INDEX `fk_role_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `fk_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;