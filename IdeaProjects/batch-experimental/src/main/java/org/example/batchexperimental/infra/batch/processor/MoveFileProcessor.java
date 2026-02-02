package org.example.batchexperimental.infra.batch.processor;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
public class MoveFileProcessor implements Tasklet {
    @Value("${app.paths.value}")
    private String pathResource;

    @Value("${app.paths.destiny}")
    private String pathDestiny;

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Path path = Paths.get(pathResource);
        Path destinyPath = Paths.get(pathDestiny);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path.getFileName())) {
            Files.move(path, destinyPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            log.warn("I/O error: {}", e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }
}
