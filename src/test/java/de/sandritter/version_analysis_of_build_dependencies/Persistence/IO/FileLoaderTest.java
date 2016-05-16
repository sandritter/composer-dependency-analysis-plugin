package de.sandritter.version_analysis_of_build_dependencies.Persistence.IO;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.sandritter.version_analysis_of_build_dependencies.Persistence.IO.FileLoader;

public class FileLoaderTest {
	
	private FileLoader fileLoader;
	private ClassLoader classLoader;
	private String filePath;
	private final String invalidFilePath = "/invalid-path/composer.json";
	
	@Before
	public void setUp() {
		fileLoader = new FileLoader();
		classLoader = getClass().getClassLoader();
		filePath = classLoader.getResource("composer.json").getPath();
	}
	
	@Test
	public void shouldLoadFile(){
		try {
			File file = fileLoader.load(filePath);
			assertEquals(filePath, file.getAbsolutePath());
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test(expected=IOException.class) 
	public void testFileLaodingException() throws IOException{
		@SuppressWarnings("unused")
		File file = fileLoader.load(invalidFilePath);
	}
}
