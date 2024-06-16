package com.tuddi.medium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionsAlgorithmsMediumPostTest {

    @Nested
    public class Frequency {

        @Test
        public void frequency_givenAListOfNumbers_shouldReturnFrequencyOfAGivenNumber() {
            int number = 4;
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 4, -4);
            int expectedFrequency = 2;

            int actualFrequency = Collections.frequency(numbers, number);

            Assertions.assertEquals(expectedFrequency, actualFrequency);
        }

        @Test
        public void frequency_givenAListOfNumbers_ifTheNumberIsNotInList_shouldReturn0() {
            int number = 100;
            List<Integer> numbers = List.of(1, 2, 3, 4, 5, 4, -4);
            int expectedFrequency = 0;

            int actualFrequency = Collections.frequency(numbers, number);

            Assertions.assertEquals(expectedFrequency, actualFrequency);
        }

        @Test
        public void frequency_givenANullList_shouldThrowANullPointerException() {
            int number = 100;

            Assertions.assertThrows(NullPointerException.class, () -> Collections.frequency(null, number));
        }

    }

    @Nested
    public class Copy {

        @Test
        public void copy_sourceHasElements_destinationIsEmpty_shouldThrowIndexOutOfBoundsException() {
            List<Integer> source = List.of(1, 2, 3, 4);
            List<Integer> destination = new ArrayList<>(4);

            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Collections.copy(destination, source));
        }

        @Test
        public void copy_sourceHasElements_destinationHasMoreThanNeededElements_shouldCopyOnlyFirstElementsOfSource() {
            List<Integer> source = List.of(1, 2, 3, 4);
            List<Integer> destination = new ArrayList<>(6);
            for (int i = 0; i < 6; i++) {
                destination.add(10);
            }

            Collections.copy(destination, source);

            Assertions.assertEquals(List.of(1, 2, 3, 4, 10, 10), destination);
        }

    }

    @Nested
    public class Disjoint {

        @Test
        public void disjoint_having2ListsWithCommonElements_shouldReturnFalse() {
            List<Integer> firstList = List.of(1, 1, 2, 3);
            List<Integer> secondList = List.of(4, 1);

            boolean result = Collections.disjoint(firstList, secondList);

            Assertions.assertFalse(result);
        }

        @Test
        public void disjoint_having2ListsWithNoCommonElements_shouldReturnTrue() {
            List<Integer> firstList = List.of(1, 1, 2, 3);
            List<Integer> secondList = List.of(4, 5);

            boolean result = Collections.disjoint(firstList, secondList);

            Assertions.assertTrue(result);
        }

    }

    @Nested
    public class IndexOfSublist {

        @Test
        public void indexOfSublist_listContainsMultipleOccurrencesOfSubList_shouldReturnFirstWhichIs0() {
            List<Integer> list = List.of(1, 2, 1, 2, 1, 2, 1);
            List<Integer> subList = List.of(1, 2, 1);
            int expectedIndex = 0;

            int actualIndex = Collections.indexOfSubList(list, subList);

            Assertions.assertEquals(expectedIndex, actualIndex);
        }

        @Test
        public void indexOfSublist_listContainsMultipleOccurrencesOfSubList_shouldReturnFirstWhichIs1() {
            List<Integer> list = List.of(2, 1, 2, 1, 2, 1, 2, 1);
            List<Integer> subList = List.of(1, 2, 1);
            int expectedIndex = 1;

            int actualIndex = Collections.indexOfSubList(list, subList);

            Assertions.assertEquals(expectedIndex, actualIndex);
        }

        @Test
        public void indexOfSublist_listContainsNoOccurrenceOfSublist_shouldReturnMinus1() {
            List<Integer> list = List.of(2, 1, 2, 1, 2, 1, 2, 1);
            List<Integer> subList = List.of(5, 4, 3, 2, 1);
            int expectedIndex = -1;

            int actualIndex = Collections.indexOfSubList(list, subList);

            Assertions.assertEquals(expectedIndex, actualIndex);
        }

    }

    @Nested
    public class LastIndexOfSublist {

        @Test
        public void lastIndexOfSublist_listContainsMultipleOccurrencesOfSubList_shouldReturnFirstWhichIs4() {
            List<Integer> list = List.of(1, 2, 1, 2, 1, 2, 1);
            List<Integer> subList = List.of(1, 2, 1);
            int expectedIndex = 4;

            int actualIndex = Collections.lastIndexOfSubList(list, subList);

            Assertions.assertEquals(expectedIndex, actualIndex);
        }

        @Test
        public void lastIndexOfSublist_listContainsOneOccurrencesOfSubList_theFirstIndexIsTheSameWithLastIndex() {
            List<Integer> list = List.of(9, 2, 1, 2, 1, 1, 1);
            List<Integer> subList = List.of(1, 2, 1);
            int expectedIndex = 2;

            int actualFirstIndex = Collections.indexOfSubList(list, subList);
            int actualLastIndex = Collections.lastIndexOfSubList(list, subList);

            Assertions.assertEquals(expectedIndex, actualFirstIndex);
            Assertions.assertEquals(expectedIndex, actualLastIndex);
        }

    }

    @Nested
    public class Reverse {

        @Test
        public void reverse_givenAList_shouldReturnItReversed() {
            List<Integer> list = new ArrayList<>(List.of(5, 4, 1, 2));
            List<Integer> originalCopyOfList = List.copyOf(list);
            List<Integer> expectedReversedList = List.of(2, 1, 4, 5);

            Collections.reverse(list);

            Assertions.assertNotEquals(list, originalCopyOfList);
            Assertions.assertEquals(expectedReversedList, list);
        }

    }

    @Nested
    public class Rotate {

        @Test
        public void rotate_givenAListAndAPositiveDistance_shouldRotateTheListToTheRight() {
            List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));
            int distance = 2;
            List<Integer> expectedRotationResult = List.of(4, 5, 1, 2, 3);

            Collections.rotate(list, distance);

            Assertions.assertEquals(expectedRotationResult, list);
        }

        @Test
        public void rotate_givenAListAndANegativeDistance_shouldRotateTheListToTheLeft() {
            List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4));
            int distance = -1;
            List<Integer> expectedRotationResult = List.of(2, 3, 4, 1);

            Collections.rotate(list, distance);

            Assertions.assertEquals(expectedRotationResult, list);
        }

    }

    @Nested
    public class Shuffle {

        @Test
        public void shuffle() {
            List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4));
            Random random = new Random(42);
            List<Integer> expectedList = List.of(4, 2, 1, 3);

            Collections.shuffle(list, random);

            Assertions.assertEquals(expectedList, list);
        }

    }

    @Nested
    public class Sort {

        @Test
        public void sortListInDescendingOrder() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
            List<Integer> expectedSortedList = List.of(6, 5, 4, 3, 2, 1);
            Comparator<Integer> ascendingOrder = Integer::compare;
            Comparator<Integer> descendingOrder = ascendingOrder.reversed();

            Collections.sort(numbers, descendingOrder);

            Assertions.assertEquals(expectedSortedList, numbers);
        }

    }

    @Nested
    public class Swap {

        @Test
        public void swap() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));
            List<Integer> expectedList = List.of(1, 3, 2, 4);

            Collections.swap(numbers, 1, 2);

            Assertions.assertEquals(expectedList, numbers);
        }

        @Test
        public void swap_jIsOutOfBounds_shouldThrowIndexOutOfBoundsException() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));

            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Collections.swap(numbers, 1, 200));
        }

        @Test
        public void swap_iIsOutOfBounds_shouldThrowIndexOutOfBoundsException() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4));

            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Collections.swap(numbers, -1, 2));
        }

    }

    @Nested
    public class AddAll {

        @Test
        public void addAll() {
            List<Integer> numbers = new ArrayList<>();
            List<Integer> expected = List.of(1, 2, 3);

            Collections.addAll(numbers, 1, 2, 3);

            Assertions.assertEquals(expected, numbers);
        }

    }

    @Nested
    public class NCopies {

        @Test
        public void nCopies_whenMutatingTheList_shouldThrowUnsupportedOperationException() {
            List<Integer> ones = Collections.nCopies(5, 1);

            Assertions.assertAll(
                    () -> Assertions.assertEquals(List.of(1, 1, 1, 1, 1), ones),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> ones.add(1)),
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> ones.set(0, 2))
            );
        }

    }

    @Nested
    public class ReplaceAll {

        @Test
        public void replaceAll() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 1, 4));
            List<Integer> expected = List.of(10, 2, 3, 10, 4);

            Collections.replaceAll(numbers, 1, 10);

            Assertions.assertEquals(expected, numbers);
        }

        @Test
        public void replaceAll_fromList() {
            List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 1, 4));
            List<Integer> expected = List.of(2, 4, 6, 2, 8);

            numbers.replaceAll(n -> n * 2);

            Assertions.assertEquals(expected, numbers);
        }

    }

    /**
     *
     *
     *
     *
     *
     *
     *
     *
     * min
     * max
     *
     *
     */


}
