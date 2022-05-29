CREATE TABLE IF NOT EXISTS `movie-awards` (
                                          `id` VARCHAR(36) NOT NULL PRIMARY KEY,
                                          `year` VARCHAR(4),
                                          `category` VARCHAR(50),
                                          `nominee` VARCHAR(50),
                                          `additionalInfo` VARCHAR(255),
                                          `won` VARCHAR(3)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;