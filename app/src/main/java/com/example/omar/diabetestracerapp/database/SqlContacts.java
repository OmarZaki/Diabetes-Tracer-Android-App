package com.example.omar.diabetestracerapp.database;

import com.example.omar.diabetestracerapp.data_model.Categories;
import com.example.omar.diabetestracerapp.data_model.InsulinDose;
import com.example.omar.diabetestracerapp.data_model.Meal;
import com.example.omar.diabetestracerapp.data_model.Messages;
import com.example.omar.diabetestracerapp.data_model.Schedule;
import com.example.omar.diabetestracerapp.data_model.User;

/**
 * Created by OMAR on 12/14/2016.
 */

public class SqlContacts {
    public static final String DATABASE_NAME="diabetesdb";


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
     -- Table `diabetesdb`.`users`
     -- -----------------------------------------------------
     */
    public static final String STATEMENT_CREATE_TABLE_USERS=
            "CREATE TABLE IF NOT EXISTS "+User._USER_TABLE+ "(\n" +
            "  "+ User._ID+ " INTEGER NOT NULL,\n" +
            "  "+ User._FIRST_NAME+ " TEXT NULL DEFAULT NULL,\n" +
            "  "+ User._LAST_NAME+ " TEXT NULL DEFAULT NULL,\n" +
            "  "+ User._PHONE_NUMBER + " TEXT NULL DEFAULT NULL,\n" +
            "  "+ User._EMAIL + " TEXT NOT NULL,\n" +
            "  "+ User._PASSWORD + " TEXT NOT NULL,\n" +
            "  "+ User._ADMIN + " INTEGER NULL DEFAULT NULL,\n" +
            "  "+ User._TYPE + " INTEGER NULL DEFAULT NULL,\n" +
            "  "+ User._TOKEN+ " TEXT NULL DEFAULT NULL,\n" +
            "  "+ User._CREATION_DATE+ " TEXT DATETIME NULL DEFAULT NULL,\n" +
            "  "+ User._ADDRESS+ " TEXT NULL DEFAULT NULL,\n" +
            "  "+ User._BIRTH_DATE+ " TEXT NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY ("+User._ID+"))\n" ;

    public static final String STATEMENT_CREATE_TABLE_INSULIN_DOSE =
            "CREATE TABLE IF NOT EXISTS "+ InsulinDose._InsulinDose_TABLE+" (\n" +
                    "  "+ InsulinDose._ID+ " INTEGER NOT NULL,\n" +
                    "  "+ InsulinDose._ORIGANL_ID+ " INTEGER NOT NULL ,\n" +
                    "  "+InsulinDose._QUANTITY + " INTEGER NULL DEFAULT NULL,\n" +
                    "  "+InsulinDose._TAKEN + " INTEGER NULL DEFAULT '0',\n" +
                    "  "+InsulinDose._DATE_TIME + " TEXT  NULL DEFAULT NULL,\n" +
                    "  "+InsulinDose._USERS_ID + " INTEGER  NOT NULL,\n" +
                    "  PRIMARY KEY ("+ InsulinDose._ID+ "))\n";

    public static final String STATEMENT_CREATE_TABLE_MEAL =
            "CREATE TABLE IF NOT EXISTS " + Meal._Meal_TABLE+" (\n" +
                    " "+ Meal._ID+" INTEGER NOT NULL,\n"+
                    " "+ Meal._TYPE+" TEXT NULL DEFAULT NULL,\n"+
                    " "+ Meal._DESCRIPTION+" TEXT NULL DEFAULT NULL,\n"+
                    " "+ Meal._DATE_TIME+" TEXT DATETIME NULL DEFAULT NULL,\n"+
                    " "+ Meal._IMAGE+" TEXT NULL DEFAULT NULL,\n"+
                    " "+ Meal._USERS_ID+" INTEGER NOT NULL,\n"+
                    " PRIMARY KEY ("+ Meal._ID+"))\n";
    /**
     *  Table `diabetesdb`.`messages`
     */
    public static final String STATEMENT_CREATE_TABLE_MESSAGES=
            "CREATE TABLE IF NOT EXISTS "+ Messages._MESSAGES_TABLE+" (\n" +
                    "  "+ Messages._ID+ " INTEGER NOT NULL,\n" +
                    "  "+ Messages._TEXT+ " TEXT NULL DEFAULT NULL,\n" +
                    "  "+ Messages._DATE_TIME+ " TEXT NULL DEFAULT NULL,\n" +
                    "  "+ Messages._USERS_ID+ " INTEGER NOT NULL,\n" +
                    "  PRIMARY KEY ("+Messages._ID+"))\n" ;

    public static final String STATEMENT_CREATE_TABLE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS " + Categories._Categories_TABLE+" (\n" +
                    " "+ Categories._ID+" INTEGER NOT NULL,\n"+
                    " "+ Categories._VALUE+" TEXT NULL DEFAULT NULL,\n"+
                    " "+ Categories._DATE_TIME+" TEXT DATETIME NULL DEFAULT NULL,\n"+
                    " "+ Categories._USERS_ID+" INTEGER NOT NULL,\n"+
                    " "+ Categories._CATEGORY_NAME_ID+" INTEGER NOT NULL,\n"+
                    " PRIMARY KEY ("+ Categories._ID+"))\n";

    /**
     * This to collect will store all the events that collected from other tables.
     * it will be stored only in the local database and will not be
     */
    public static final java.lang.String STATEMENT_CREATE_TABLE_SCHEDULE =
            "CREATE TABLE IF NOT EXISTS "+ Schedule._SCHEDULE_TABLE+" (\n" +
                    "  "+ Schedule._COL_TITLE + " TEXT NOT NULL ,\n" +
                    "  "+ Schedule._COL_DATE   + " TEXT NULL DEFAULT NULL" +" , "+
                    "  "+ Schedule._COL_TYPE   + " STRING NULL DEFAULT NULL" +
                    ")\n";

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



    /**
     * SETUP parameters
     */
    public static final String STATEMENT_CREATE_TABLE_SETUPS="\n" +
            "SET SQL_MODE=@OLD_SQL_MODE;\n" +
            "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;\n" +
            "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;";



}
