package org.junit.BuildInExtensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static freemarker.template.utility.Collections12.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/13 19:50
 */
public class SharedTempDirectoryDemo {

    @TempDir
    static Path sharedTempDir;

    @Test
    void writeItemsToFile() throws IOException {
        Path file = sharedTempDir.resolve("test.txt");

        //new ListWriter(file).write("a", "b", "c");

        assertEquals(singletonList("a,b,c"), Files.readAllLines(file));
    }

    @Test
    void anotherTestThatUsesTheSameTempDir() {
        // use sharedTempDir
    }
}
