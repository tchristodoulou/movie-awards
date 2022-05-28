CREATE TABLE IF NOT EXISTS `movie-awards` (
                                          `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `year` varchar(4),
                                          `category` varchar(50),
                                          `nominee` varchar(50),
                                          'additionalInfo' varchar(255),
                                          'won' varchar(3)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;