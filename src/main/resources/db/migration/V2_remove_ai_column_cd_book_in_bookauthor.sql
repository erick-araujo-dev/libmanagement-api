ALTER TABLE `libmanagementdb`.`book_author`
DROP FOREIGN KEY `fk_book_has_author_book`;
ALTER TABLE `libmanagementdb`.`book_author`
CHANGE COLUMN `cd_book` `cd_book` INT NOT NULL ;
ALTER TABLE `libmanagementdb`.`book_author`
ADD CONSTRAINT `fk_book_has_author_book`
  FOREIGN KEY (`cd_book`)
  REFERENCES `libmanagementdb`.`book` (`cd_book`);