package com.github.ksahu750.type.list;

import com.github.ksahu750.context.Context;
import java.io.Serializable;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

public class RocksListTest {

  @BeforeAll
  public static void setup() {
    Context context = Context.INSTANCE;
  }

  @Test
  public void testList() {
    final RocksList<MockObj> list = RocksList.withName("list");

    IntStream.range(0, 10).mapToObj(i -> new MockObj(i, "abcd")).forEach(list::push);

    System.out.println("Going to read");
    final RocksList<MockObj> rereadList = RocksList.withName("list");
    for (MockObj obj : rereadList) {
      System.out.println(obj);
    }

    rereadList.clear();
    System.out.println("After clearing rereadList");
    for (MockObj obj : rereadList) {
      System.out.println(obj);
    }
    System.out.println("After clearing list");
    for (MockObj obj : list) {
      System.out.println(obj);
    }
  }

  @Test
  public void printAll() {
    final RocksDB db = Context.INSTANCE.getInjector().getInstance(RocksDB.class);
    final RocksIterator iterator = db.newIterator();
    for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
      System.out.println("Key - " + new String(iterator.key()));
    }
  }

  record MockObj(int a, String b) implements Serializable {}
}
