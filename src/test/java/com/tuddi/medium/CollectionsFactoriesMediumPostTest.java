package com.tuddi.medium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionsFactoriesMediumPostTest {

    @Nested
    public class Empty {

        @Test
        public void emptyList_shouldHaveTheSameReference() {
            Assertions.assertAll(
                    () -> Assertions.assertTrue(Collections.emptyList() == Collections.emptyList()),
                    () -> Assertions.assertTrue(Collections.emptyList() == Collections.EMPTY_LIST)
            );
        }

        @Test
        public void emptyList_cantAddElementsToIt_shouldThrowUnsupportedOperationException() {
            List<Integer> numbers = Collections.emptyList();

            Assertions.assertAll(
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.add(1)),
                    () -> {
                        numbers.removeAll(List.of(1,2, 3)); // should not fail, nothing to remove
                    },
                    () -> {
                        numbers.remove((Object) 0); // should not fail, no numbers in the list
                    },
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.remove(0)), // should fail because index is not set
                    () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.set(0, 1)),
                    () -> Assertions.assertEquals(Collections.emptyList(), numbers) // should remain the same
            );
            // You're probably wondering why cast to Object fails for remove. It is because it searches for the object to remove it,
            // while the remove with integer, will try to remove it from the specified position
        }

    }

    @Nested
    public class Singleton {

        @Test
        public void singletonList_cantBeModified_shouldThrowUnsupportedOperationException() {
           List<Integer> numbers = Collections.singletonList(1);

           Assertions.assertAll(
                   () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.add(2)),
                   () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.set(0, 2)),
                   () -> Assertions.assertThrows(UnsupportedOperationException.class, () -> numbers.remove(1))
           );
        }

        @Test
        public void singletonList_elementIsModified_theChangeIsVisibleInsideTheList() {
            List<AtomicInteger> numbers = Collections.singletonList(new AtomicInteger(0));

            numbers.get(0).incrementAndGet();

            Assertions.assertEquals(1, numbers.get(0).get());
        }

    }

}
