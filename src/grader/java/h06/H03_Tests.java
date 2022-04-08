package h06;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.*;

import org.junit.jupiter.api.Test;

import h06.*;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("H06")
public class H03_Tests
{
	//////////////////////////
	// MyIndexHoppingHashMap
	//////////////////////////

	@Test
	public void H03_MyIndexHoppingHashMapBasic()
	{
		// Constructor
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(10, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		new MyIndexHoppingHashMap<Object, Object>(10, 2, 0.75, hashFunction);

		// Type parameter
		try
		{
			//new MyIndexHoppingHashMap<MyMap<Object, Object>, Object>(10, 2, 0.75, null);
			//fail("MyIndexHoppingHashMap type parameter is not bound to classes.");
		}
		catch (Exception e){}

		// Interface
		Class<?>[] interfaces = MyIndexHoppingHashMap.class.getInterfaces();
		assertEquals(1, interfaces.length, "MyIndexHoppingHashMap implementiert nicht wie erwartet 1 Interface, sondern: " + interfaces.length);
		assertEquals(MyMap.class.getName(), interfaces[0].getName(), "MyIndexHoppingHashMap implementiert nicht wie erwartet das Interface MyMap, sondern: " + interfaces[0].getName());
	}

	////////
	// Put
	////////

	@Test
	public void H03_MyIndexHoppingHashMapPutSingle()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(null, hashMap.put("test", "value"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "value", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
					occupied));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapPutReplace()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(null, hashMap.put("test", "value"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "value", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
					occupied));

			assertEquals("value", hashMap.put("test", "a"));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "a", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
					occupied));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapPutMultiple()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(null, hashMap.put("test", "value"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "value", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
					occupied));

			assertEquals(null, hashMap.put("key", 2));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", "key"},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "value", 2},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, true},
					occupied));

			assertEquals(null, hashMap.put("m", "a"));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true},
					occupied));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapPutMultipleResize()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(4, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(4, 2, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(null, hashMap.put("test", "value"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, "value", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, true, false},
					occupied));

			assertEquals(null, hashMap.put("a", 2));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, "a", "test", null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, 2, "value", null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, true, true, false},
					occupied));

			assertEquals(null, hashMap.put("m", "l"));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, "a", "test", null, null, "m", null, null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, 2, "value", null, null, "l", null, null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, true, true, false, false, true, false, false},
					occupied));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	///////////
	// Remove
	///////////

	@Test
	public void H03_MyIndexHoppingHashMapRemoveEmpty()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(10, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(10, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(null, hashMap.remove("test"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false },
					occupied));

		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapRemoveSingle()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			theKeys.set(hashMap, new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "test", null});
			theValues.set(hashMap, new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, "value", null});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false});

			assertEquals("value", hashMap.remove("test"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false},
					occupied));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapRemoveMultiple()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			theKeys.set(hashMap, new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"});
			theValues.set(hashMap, new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true});

			assertEquals("value", hashMap.remove("test"));

			Object[] keys = (Object[])theKeys.get(hashMap);
			Object[] values = (Object[])theValues.get(hashMap);
			Object[] occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, null, "key"},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, null, 2},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true},
					occupied));

			assertEquals("a", hashMap.remove("m"));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, "key"},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, 2},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true},
					occupied));


			assertEquals(2, hashMap.remove("key"));

			keys = (Object[])theKeys.get(hashMap);
			values = (Object[])theValues.get(hashMap);
			occupied = toObjectArray((boolean[])occupiedSinceLastRehash.get(hashMap));

			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
					keys));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
					values));
			assertEquals(true, Arrays.deepEquals(
					new Object[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true},
					occupied));

		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	/////////////
	// Contains
	/////////////

	@Test
	public void H03_MyIndexHoppingHashMapContainsKeyTrue()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			theKeys.set(hashMap, new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"});
			theValues.set(hashMap, new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true});

			assertEquals(true, hashMap.containsKey("test"));
			assertEquals(true, hashMap.containsKey("key"));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapContainsKeyFalse()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			assertEquals(false, hashMap.containsKey("test"));

			theKeys.set(hashMap, new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"});
			theValues.set(hashMap, new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true});

			assertEquals(false, hashMap.containsKey("l"));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	/////////////
	// GetValue
	/////////////

	@Test
	public void H03_MyIndexHoppingHashMapGetValuePositive()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			theKeys.set(hashMap, new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"});
			theValues.set(hashMap, new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true});

			assertEquals("value", hashMap.getValue("test"));
			assertEquals(2, hashMap.getValue("key"));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void H03_MyIndexHoppingHashMapGetValueNegative()
	{
		HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(15, 0);
		LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
		MyIndexHoppingHashMap<Object, Object> hashMap = new MyIndexHoppingHashMap<Object, Object>(15, 1.5, 0.5, hashFunction);

		try
		{
			Field theKeys = hashMap.getClass().getDeclaredField("theKeys");
			theKeys.setAccessible(true);
			Field theValues = hashMap.getClass().getDeclaredField("theValues");
			theValues.setAccessible(true);
			Field occupiedSinceLastRehash = hashMap.getClass().getDeclaredField("occupiedSinceLastRehash");
			occupiedSinceLastRehash.setAccessible(true);

			theKeys.set(hashMap, new Object[] {null, null, null, null, "m", null, null, null, null, null, null, null, null, "test", "key"});
			theValues.set(hashMap, new Object[] {null, null, null, null, "a", null, null, null, null, null, null, null, null, "value", 2});
			occupiedSinceLastRehash.set(hashMap, new boolean[] {false, false, false, false, true, false, false, false, false, false, false, false, false, true, true});


			assertEquals(null, hashMap.getValue("testa"));
			assertEquals(null, hashMap.getValue("keya"));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}

  private Object[] toObjectArray(boolean[] boolArray)
  {
    Object[] booleanArray = new Boolean[boolArray.length];
    for (int i = 0; i < boolArray.length; i++)
    {
      booleanArray[i] = boolArray[i];
    }
    return booleanArray;
  }
}
