package com.tuddi.medium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CollectionsWrappersMediumPostTest {

    @Nested
    public class ArraysAsExample {

        @Test
        public void arraysAsList_changesOnBackedArrayAreVisibleOnList_changesOnListAreVisibleOnArray_andChangingTheSizeOfListIsUnsupported() {
            Integer[] arr = {1, 2, 3, 4};
            List<Integer> list = Arrays.asList(arr);

            arr[0] = -1;
            list.set(1, -2);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(List.of(-1, -2, 3, 4), list),
                    () -> Assertions.assertArrayEquals(new Integer[]{-1, -2, 3, 4}, arr),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add(5))
            );
        }

    }

    @Nested
    public class Unmodifiable {

        @Test
        public void unmodifiableList_ifAddingOrRemovingOrRetainOrSet_shouldThrowUnsupportedOperationException() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));
            List<Integer> unmodifiableNumbers = Collections.unmodifiableList(numbers);

            Assertions.assertAll(
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.add(5)),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.addAll(List.of(5, 6))),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.remove(1)),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.removeAll(List.of(1, 2))),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.set(0, -1)),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.set(0, unmodifiableNumbers.get(0))),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> unmodifiableNumbers.retainAll(List.of(1, 2)))
            );
        }

        @Test
        public void unmodifiableList_modifyingTheOriginalList_shouldAlterTheWrapper() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));
            List<Integer> unmodifiableNumbers = Collections.unmodifiableList(numbers);
            List<Integer> expected = new ArrayList<>(List.of(-1, -2, 3, 4));

            numbers.set(0, -1);
            numbers.set(1, -2);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(expected, numbers),
                    () -> Assertions.assertEquals(expected, unmodifiableNumbers)
            );
        }

        @Test
        public void unmodifiableList_ofAtomicIntegers_modifyingAtomicIntegersIsPossibleThroughTheUnmodifiableList() {
            List<AtomicInteger> numbers = new ArrayList<>(List.of(new AtomicInteger(0), new AtomicInteger(1)));
            List<AtomicInteger> unmodifiableNumbers = Collections.unmodifiableList(numbers);

            unmodifiableNumbers.get(0).incrementAndGet();

            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, numbers.get(0).get()),
                    () -> Assertions.assertEquals(1, unmodifiableNumbers.get(0).get())
            );
        }

    }

    @Nested
    public class Synchronized {

        @Test
        public void synchronizedList_shouldAdd1MillionElementsSafely() {
            int capacity = 10_000, numberOfThreads = 10;
            int taskSize = capacity / numberOfThreads;
            List<Integer> list = new ArrayList<>(capacity);
            List<Integer> synchronizedList = Collections.synchronizedList(list);
            List<Integer> expectedList = IntStream.range(0, capacity).boxed().toList();

            // Generating a list with elements from [0, n), where n is the size of the list
            try (ExecutorService es = Executors.newFixedThreadPool(numberOfThreads)) {
                for (int i = 0; i < numberOfThreads; i++) {
                    final int iValue = i;
                    es.submit(() -> {
                        for (int j = 0; j < taskSize; j++) {
                            synchronizedList.add(iValue * taskSize + j);
                        }
                    });
                }
            }

            Collections.sort(synchronizedList);
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expectedList.size(), synchronizedList.size()),
                    () -> Assertions.assertEquals(expectedList, synchronizedList)
            );
        }

    }

    @Nested
    public class Checked {

        @Test
        public void anotherTypesInTheList() {
            String string = "Hello, this is not a number!";
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));
            List<Object> expected = List.of(1, 2, 3, 4, string);

            List referenceToNumbersWithoutCast = numbers;
            referenceToNumbersWithoutCast.add(string);

            Assertions.assertEquals(expected, numbers);
        }

        @Test
        public void checkedList_ifTryingToAddAString_shouldThrowClassCastException() {
            String string = "Hello, this is not a number!";
            List<Integer> numbers = Collections.checkedList(new ArrayList<>(List.of(1, 2, 3, 4)), Integer.class);
            List<Integer> expected = List.of(1, 2, 3, 4);

            List referenceToNumbersWithoutCast = numbers;

            Assertions.assertAll(
                    () -> Assertions.assertThrows(ClassCastException.class, () -> referenceToNumbersWithoutCast.add(string)),
                    () -> Assertions.assertEquals(expected, numbers)
            );
        }

    }

}
