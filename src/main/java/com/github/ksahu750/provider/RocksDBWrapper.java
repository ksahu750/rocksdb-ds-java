package com.github.ksahu750.provider;

import com.github.ksahu750.util.SerializationUtils;
import com.github.ksahu750.util.WriteBatchWrapper;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.WriteOptions;

@Singleton
public class RocksDBWrapper {

  private final RocksDB underlying;

  @Inject
  public RocksDBWrapper(RocksDB rocksDB) {
    this.underlying = rocksDB;
  }

  public <T> Optional<T> get(String key) {
    try {
      final byte[] data = underlying.get(key.getBytes(StandardCharsets.UTF_8));
      if (Objects.isNull(data)) {
        return Optional.empty();
      }
      return Optional.of(SerializationUtils.deserialize(data));
    } catch (RocksDBException e) {
      throw new RuntimeException("Failed to get value for key", e);
    }
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

  public boolean exists(String key) {
    try {
      return Objects.nonNull(underlying.get(key.getBytes(StandardCharsets.UTF_8)));
    } catch (RocksDBException e) {
      throw new RuntimeException("Failed to get value for key", e);
    }
  }

  public void write(WriteBatchWrapper writeBatch) {
    try (WriteOptions writeOptions = new WriteOptions()) {
      writeOptions.setSync(true);
      write(new WriteOptions(), writeBatch);
    }
  }

  public void write(WriteOptions writeOpts, WriteBatchWrapper writeBatch) {
    try {
      underlying.write(writeOpts, writeBatch.unwrap());
    } catch (RocksDBException e) {
      throw new RuntimeException("Failed to write batch", e);
    }
  }

  public RocksDB unwrap() {
    return underlying;
  }
}
