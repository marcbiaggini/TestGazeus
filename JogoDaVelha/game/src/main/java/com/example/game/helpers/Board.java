package com.example.game.helpers;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by juan.villa on 16/06/17.
 *
 * @author Juan Villa marcbiaggini@gmail.com
 * @date 16/06/17
 */

public class Board {

  private int size;

  private Cell[] data;

  public Board(int size) {
    this.size = size;
    this.data = new Cell[size * size];
  }

  public int getSize() {
    return size;
  }


  public int getNumTiles() {
    return data.length;
  }

  public void place(Cell cell, int r, int c) {
    place(cell, toIndex(r, c));
  }

  public void place(Cell cell, int i) {
    data[i] = cell;
  }

  private int toIndex(int r, int c) {
    return r * size + c;
  }

  public boolean isEmpty(int r, int c) {
    return data[toIndex(r, c)] == null;
  }

  public Cell getValue(int r, int c) {
    return getValue(toIndex(r, c));
  }

  public Cell getValue(int i) {
    return data[i];
  }

  public void prettyPrint(PrintStream out) {

    for (int i = 0; i < data.length; i++) {
      out.print(data[i] != null ? data[i] : " ");
      if (i % size == size - 1) {
        out.println();
      }
      else {
        out.print(" | ");
      }
    }
  }

  public Collection<Tuple> toTuples() {
    final List<Tuple> list = new ArrayList<Tuple>();

    for (int offset = 0; offset < size; offset++) {
      Cell[] column = new Cell[size];
      Cell[] row = new Cell[size];
      for (int i = 0; i < size; i++) {
        column[i] = data[offset + size * i];
        row[i] = data[offset * size + i];
      }
      list.add(new Tuple(column));
      list.add(new Tuple(row));
    }

    Cell[] diag1 = new Cell[size];
    Cell[] diag2 = new Cell[size];
    for (int i = 0; i < size; i++) {
      diag1[i] = data[(size + 1) * i];
      diag2[i] = data[(size - 1) * (i + 1)];
    }
    list.add(new Tuple(diag1));
    list.add(new Tuple(diag2));

    return list;
  }


  public static class Tuple implements Iterable<Cell> {

    Cell[] data;

    public Tuple(Cell[] data) {
      this.data = data;
    }

    public int getLength() {
      return data.length;
    }

    public Cell getValue(int i) {
      return data[i];
    }

    public boolean contains(Cell cell) {
      for (Cell t : data) {
        if (t == cell) return true;
      }
      return false;
    }

    public Iterator<Cell> iterator() {
      return new Iterator<Cell>() {

        int pos = 0;

        @Override
        public boolean hasNext() {
          return pos < data.length;
        }

        @Override
        public Cell next() {
          return data[pos++];
        }

        @Override
        public void remove() {
          throw new UnsupportedOperationException("Cannot remove values from Tuple");
        }

      };
    }

    @Override
    public String toString() {
      return super.toString() + Arrays.toString(data);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(data);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Tuple other = (Tuple) obj;
      if (!Arrays.equals(data, other.data)) return false;
      return true;
    }

  }
}
