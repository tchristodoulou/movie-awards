CREATE TABLE IF NOT EXISTS `movie-awards` (
                                          `id` VARCHAR(36) NOT NULL PRIMARY KEY,
                                          `year` VARCHAR(4),
                                          `category` VARCHAR(100),
                                          `nominee` VARCHAR(1000),
                                          `additionalInfo` VARCHAR(200),
                                          `won` VARCHAR(200)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;