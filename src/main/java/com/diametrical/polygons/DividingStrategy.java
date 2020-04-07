package com.diametrical.polygons;

import java.util.List;

public interface DividingStrategy<T> {
   List<T> divide(T divisible);
}
