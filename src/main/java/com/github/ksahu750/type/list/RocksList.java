package com.github.ksahu750.type.list;

import com.github.ksahu750.type.BaseRocksType;
import com.github.ksahu750.wrapper.WriteBatchWrapper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import lombok.ToString;

@ToString(callSuper = true)
public class RocksList<T> extends BaseRocksType implements Iterable<T> {

  private final String name;

  private RocksList(String name) {
    super(DataType.LIST);
    this.name = name;
    if (!dbWrapper().exists(name)) {
      ListMetadata metadata = new ListMetadata();
      dbWrapper().put(name, metadata);
    }
  }

  public static <T> RocksList<T> withName(String name) {
    return new RocksList<>(name);
  }

  private ListMetadata metadata() {
    return dbWrapper().<ListMetadata>get(name).orElseThrow();
  }

  /**
   * Query metadata and obtain current size
   *
   * @return size of list
   */
  public int size() {
    return metadata().size();
  }

  public void push(T value) {
    try (WriteBatchWrapper batch = new WriteBatchWrapper()) {
      final ListMetadata metadata = metadata();
      batch.put(keyForIdx(metadata.tailIdx++), value);
      batch.put(name, metadata);
      dbWrapper().write(batch);
    }
  }

  public T pop() {
    final ListMetadata metadata = metadata();
    final String currentHeadKey = keyForIdx(metadata.headIdx);
    T value = dbWrapper().<T>get(currentHeadKey).orElseThrow();
    try (WriteBatchWrapper batch = new WriteBatchWrapper()) {
      batch.delete(currentHeadKey);
      metadata.headIdx--;
      batch.put(name, metadata);
      dbWrapper().write(batch);
    }
    return value;
  }

  public boolean isEmpty() {
    final ListMetadata metadata = metadata();
    return metadata.headIdx == metadata.tailIdx;
  }

  public void clear() {
    try (final WriteBatchWrapper batch = new WriteBatchWrapper()) {
      final ListMetadata metadata = metadata();
      IntStream.range(metadata.headIdx, metadata.tailIdx)
          .mapToObj(this::keyForIdx)
          .forEach(batch::delete);
      metadata.headIdx = 0;
      metadata.tailIdx = 0;
      batch.put(name, metadata);
      dbWrapper().write(batch);
    }
  }

  private String keyForIdx(int idx) {
    return name + DELIMITER + idx;
  }

  @Override
  public Iterator<T> iterator() {
    return new ListIterator<>();
  }

  private class ListIterator<R> implements Iterator<R> {

    private ListMetadata metadata;

    private int offset;

    public ListIterator() {
      this.metadata = metadata();
      this.offset = 0;
    }

    @Override
    public boolean hasNext() {
      return offset < size();
    }

    @Override
    public R next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      final int nextIdx = metadata.headIdx + offset++;
      final String nextIdxKey = keyForIdx(nextIdx);
      return dbWrapper().<R>get(nextIdxKey).orElseThrow();
    }
  }
}
