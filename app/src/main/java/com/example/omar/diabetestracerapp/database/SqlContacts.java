package com.example.omar.diabetestracerapp.database;

/**
 * Created by OMAR on 12/14/2016.
 */

public class SqlContacts {

    /**
     * Constructor should be a private to prevent being initiated accidentally.
     */
    public SqlContacts(){}

    // data
    public static final String DATA_INTEGER=" integer ";
    public static final String DATA_TEXT=" text ";
    public static final String PRIMARY_KEY_AUTO=" primary key autoincrement ";

    // SQL statements

    /**
     *
     -- -----------------------------------------------------
     -- Schema diabetesdb
     -- -----------------------------------------------------
     */
    public static final String CREATE_SCHEMA=
            "CREATE SCHEMA IF NOT EXISTS `diabetesdb` DEFAULT CHARACTER SET utf8 ;\n" +
            "     USE `diabetesdb` ;";


    /**
     *
     -- -----------------------------------------------------
     -- Table `diabetesdb`.`users`
     -- -----------------------------------------------------
     */
    public static final String STATEMENT_CREATE_TABLE_USERS=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`users` (\n" +
            "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `fname` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lname` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `phone_number` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `email` VARCHAR(45) NOT NULL,\n" +
            "  `password` VARCHAR(45) NOT NULL,\n" +
            "  `admin` TINYINT(1) NULL DEFAULT NULL,\n" +
            "  `type` TINYINT(1) NULL DEFAULT NULL,\n" +
            "  `token` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `creation_date` DATETIME NULL DEFAULT NULL,\n" +
            "  `address` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";

    /**
     *  Table `diabetesdb`.`appointments`
     */
    public static final String STATEMENT_CREATE_TABLE_APPOINTMENTS=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`appointments` (\n" +
            "  `id` INT(11) NOT NULL,\n" +
            "  `title` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `date_time` DATETIME NULL DEFAULT NULL,\n" +
            "  `assessment` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `blood_pressure` FLOAT NULL DEFAULT NULL,\n" +
            "  `weight` FLOAT NULL DEFAULT NULL,\n" +
            "  `Users_id` INT(11) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `fk_Appointments_Users1_idx` (`Users_id` ASC),\n" +
            "  CONSTRAINT `fk_Appointments_Users1`\n" +
            "    FOREIGN KEY (`Users_id`)\n" +
            "    REFERENCES `diabetesdb`.`users` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";

    /**
     *   Table `diabetesdb`.`category_name`
     */
    public static final String STATEMENT_CREATE_TABLE_CATEGORY_NAME=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`category_name` (\n" +
            "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `title` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;\n";


    /**
     * Table `diabetesdb`.`categories`
     */
    public static final String STATEMENT_CREATE_TABLE_CATEGORYIES=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`categories` (\n" +
            "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `date_time` DATETIME NULL DEFAULT NULL,\n" +
            "  `value` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `Users_id` INT(11) NOT NULL,\n" +
            "  `Category_name_id` INT(11) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `fk_Categories_Users1_idx` (`Users_id` ASC),\n" +
            "  INDEX `fk_Categories_Category_name1_idx` (`Category_name_id` ASC),\n" +
            "  CONSTRAINT `fk_Categories_Category_name1`\n" +
            "    FOREIGN KEY (`Category_name_id`)\n" +
            "    REFERENCES `diabetesdb`.`category_name` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_Categories_Users1`\n" +
            "    FOREIGN KEY (`Users_id`)\n" +
            "    REFERENCES `diabetesdb`.`users` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";
    /**
     *  Table `diabetesdb`.`insulindose`
     */
    public static final String STATEMENT_CREATE_TABLE_INSULINEDOSE=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`insulindose` (\n" +
            "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `quantity` FLOAT NULL DEFAULT NULL,\n" +
            "  `taken` TINYINT(1) NULL DEFAULT '0',\n" +
            "  `date_time` DATETIME NULL DEFAULT NULL,\n" +
            "  `Users_id` INT(11) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `fk_InsulinDose_Users1_idx` (`Users_id` ASC),\n" +
            "  CONSTRAINT `fk_InsulinDose_Users1`\n" +
            "    FOREIGN KEY (`Users_id`)\n" +
            "    REFERENCES `diabetesdb`.`users` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;\n";

    /**
     * Table `diabetesdb`.`meal`
     */
    public static final String STATEMENT_CREATE_TABLE_MEAL=
            "CREATE TABLE IF NOT EXISTS `diabetesdb`.`meal` (\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `type` VARCHAR(45) NULL DEFAULT NULL,\n" +
                    "  `date_time` DATETIME NULL DEFAULT NULL,\n" +
                    "  `description` VARCHAR(45) NULL DEFAULT NULL,\n" +
                    "  `image` VARCHAR(45) NULL DEFAULT NULL,\n" +
                    "  `Users_id` INT(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  INDEX `fk_Meal_Users_idx` (`Users_id` ASC),\n" +
                    "  CONSTRAINT `fk_Meal_Users`\n" +
                    "    FOREIGN KEY (`Users_id`)\n" +
                    "    REFERENCES `diabetesdb`.`users` (`id`)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION)\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;" ;
    /**
     *  Table `diabetesdb`.`messages`
     */
    public static final String STATEMENT_CREATE_TABLE_MESSAGES= "CREATE TABLE IF NOT EXISTS `diabetesdb`.`messages` (\n" +
            "  `id` INT(11) NOT NULL,\n" +
            "  `text` TEXT NULL DEFAULT NULL,\n" +
            "  `date_time` DATETIME NULL DEFAULT NULL,\n" +
            "  `Users_id` INT(11) NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  INDEX `fk_Messages_Users1_idx` (`Users_id` ASC),\n" +
            "  CONSTRAINT `fk_Messages_Users1`\n" +
            "    FOREIGN KEY (`Users_id`)\n" +
            "    REFERENCES `diabetesdb`.`users` (`id`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;\n";
    /**
     * SETUP parameters
     */
    public static final String STATEMENT_CREATE_TABLE_SETUPS="\n" +
            "SET SQL_MODE=@OLD_SQL_MODE;\n" +
            "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\n" +
            "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;";


}
