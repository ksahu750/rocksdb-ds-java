package com.github.ksahu750.wrapper;

import com.github.ksahu750.util.SerializationUtils;
import java.nio.charset.StandardCharsets;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteBatch;

public class WriteBatchWrapper implements AutoCloseable {

  private final WriteBatch underlying;

  public WriteBatchWrapper() {
    this.underlying = new WriteBatch();
  }

  public <T> void put(String key, T value) {
    try {
      underlying.put(key.getBytes(StandardCharsets.UTF_8), SerializationUtils.serialize(value));
    } catch (RocksDBException e) {
      throw new RuntimeException("Failed to get value for key", e);
    }
  }

  public void delete(String key) {
    try {
      underlying.delete(key.getBytes(StandardCharsets.UTF_8));
    } catch (RocksDBException e) {
      throw new RuntimeException("Failed to get value for key", e);
    }
  }

  @Override
  public void close() {
    underlying.close();
  }

  public WriteBatch unwrap() {
    return underlying;
  }
}
