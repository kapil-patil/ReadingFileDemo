package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;

import com.example.repo.BookEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonParser {

	List<Book> bookList = new ArrayList<>();
	List<BookEntity> bookEntityList = new ArrayList<>();
	ModelMapper mapper = new ModelMapper();

	public List<Book> loadFile(String path) throws IOException {

		long begin = System.currentTimeMillis();

		
		/*  String jsonRecords = FileUtils.readFileToString(new File(path),
		  StandardCharsets.UTF_8);
		  
		  ObjectMapper mapper = new ObjectMapper();
		  
		  // Deserialize the JSON string into an array of User objects book List =
		  bookList = mapper.readValue(jsonRecords, new TypeReference<List<Book>>() { }); */
		 

		String jsonRecords = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<List<Book>>() {
		}.getType();
		bookList = gson.fromJson(jsonRecords, listType);
		
		System.out.println("size of book list "+bookList.size());

		long end = System.currentTimeMillis();

		long time = end - begin;

		System.out.println("Elapsed Time: " + time + " milli seconds");

		return bookList;

	}

	public void converttoEntity(List<Book> books) {

		TypeMap<Book, BookEntity> propertyMapper = mapper.createTypeMap(Book.class, BookEntity.class);
		//propertyMapper.addMapping(Book::getId, BookEntity::setIdNumber);
		propertyMapper.addMapping(Book::getName, BookEntity::setTitle);

		for (Book b : books) {

			BookEntity entity = this.mapper.map(b, BookEntity.class);
			System.out.println(entity);

		}

		// books.stream().forEach(b-> this.mapper.map(BookEntity, Book.class)).coll;

	}

	public static void main(String[] args) throws IOException {
		JsonParser obj = new JsonParser();
		obj.loadFile("D:\\PSRE\\output\\large_file.json");
		// obj.converttoEntity(obj.loadFile("D:\\PSRE\\output\\large_file.json"));
	}
}
