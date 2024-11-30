package org.example.springbootjpa.scheduler;

import org.example.springbootjpa.entity.Book;
import org.example.springbootjpa.repository.BookRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component
@EnableScheduling
public class DatabaseUpdateScheduler {

    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUpdateScheduler.class);

    public DatabaseUpdateScheduler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void updateDatabaseRecords() {
        logger.info("Запускается обновление записей в БД...");
        try {
            List<Book> booksToUpdate = bookRepository.findByStatus("недоступен");
            for (Book book : booksToUpdate) {
                book.setStatus("доступен");
            }
            bookRepository.saveAll(booksToUpdate);
            logger.info("Обновление записей завершено: {} записей обновлено.", booksToUpdate.size());
        } catch (Exception e) {
            logger.error("Ошибка при обновлении записей: {}", e.getMessage(), e);
        }
    }
}