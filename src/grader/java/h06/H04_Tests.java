package h06;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("H06")
public class H04_Tests
{
  /////////////////
  // KeyValuePair
  /////////////////

  @Test
  public void H04_KeyValuePair()
  {
    // Constructor
    KeyValuePair<Object, Object> keyValuePair = new KeyValuePair<Object, Object>(10, "test");

    // Key
    assertEquals(10, keyValuePair.getKey());

    // Value
    assertEquals("test", keyValuePair.getValue());
    keyValuePair.setValue(true);
    assertEquals(true, keyValuePair.getValue());
  }


  //////////////////////////
  // MyIndexHoppingHashMap
  //////////////////////////

  @Test
  public void H04_MyListsHashMapBasic()
  {
    // Constructor
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(10, 0);
    new MyListsHashMap<Object, Object>(hashFunction);

    // Type parameter
    try
    {
    //  new MyListsHashMap<MyMap<Object, Object>, Object>(null);
    //  fail("MyListsHashMap type parameter is not bound to classes.");
    }
    catch (Exception e){}

    // Interface
    Class<?>[] interfaces = MyListsHashMap.class.getInterfaces();
    assertEquals(1, interfaces.length);
    assertEquals(MyMap.class.getName(), interfaces[0].getName());
  }

  ////////
  // Put
  ////////

  @Test
  public void H04_MyListsHashMapMapPutSingle()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

      assertEquals(null, hashMap.put("test", "value"));
      int containerIndex = hashFunction.apply("test");

	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      tableReference[containerIndex].add(new KeyValuePair<Object, Object>("test", "value"));

      LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }


  @Test
  public void H04_MyListsHashMapPutReplace()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

      assertEquals(null, hashMap.put("test-replace", "value"));
      int containerIndex = hashFunction.apply("test-replace");

	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      tableReference[containerIndex].add(new KeyValuePair<Object, Object>("test-replace", "value"));

      LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);

      assertEquals("value", hashMap.put("test-replace", "a"));

      tableReference[containerIndex].clear();
      tableReference[containerIndex].add(new KeyValuePair<Object, Object>("test-replace", "a"));

      table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  @Test
  public void H04_MyListsHashMapPutMultiple()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      int containerIndexTest = hashFunction.apply("test");
      int containerIndexE = hashFunction.apply("e");
      int containerIndexA = hashFunction.apply("a");

      assertEquals(containerIndexTest, containerIndexE);

      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      tableReference[containerIndexTest].addFirst(new KeyValuePair<Object, Object>("test", "value"));

      assertEquals(null, hashMap.put("test", "value"));
      LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);

      tableReference[containerIndexE].addFirst(new KeyValuePair<Object, Object>("e", 2));

      assertEquals(null, hashMap.put("e", 2));
      table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);

      tableReference[containerIndexA].addFirst(new KeyValuePair<Object, Object>("a", "a"));

      assertEquals(null, hashMap.put("a", "a"));
      table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);
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
  public void H04_MyListsHashMapRemoveEmpty()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      assertEquals(null, hashMap.remove("test"));
      LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  @Test
  public void H04_MyListsHashMapRemoveSingle()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      int containerIndex = hashFunction.apply("hallo");
  	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());
      for (int i = 0; i < 13; i++)
      {
    	  table[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }
      table[containerIndex].addFirst(new KeyValuePair<Object, Object>("hallo", "wert"));

      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);
      tableField.set(hashMap, table);

  	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());
      for (int i = 0; i < 13; i++)
      {
    	  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      assertEquals("wert", hashMap.remove("hallo"));
      LinkedList<KeyValuePair<Object, Object>>[] resultTable = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, table);
    }
    catch (Exception e)
    {
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	fail(sw.toString());
    }
  }

@SuppressWarnings("unchecked")
@Test
  public void H04_MyListsHashMapRemoveMultiple()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
	  int containerIndexTest = hashFunction.apply("test");
	  int containerIndexA = hashFunction.apply("a");
	  int containerIndexE = hashFunction.apply("e");

	  assertEquals(containerIndexTest, containerIndexE);

	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());
	  for (int i = 0; i < 13; i++)
	  {
	    table[i] = new LinkedList<KeyValuePair<Object, Object>>();
	  }
	  table[containerIndexTest].addFirst(new KeyValuePair<Object, Object>("test", "wert"));
	  table[containerIndexA].addFirst(new KeyValuePair<Object, Object>("a", 12));
	  table[containerIndexE].addFirst(new KeyValuePair<Object, Object>("e", 10));

      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);
      tableField.set(hashMap, table);

	  LinkedList<KeyValuePair<Object, Object>>[] tableReference = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());
	  for (int i = 0; i < 13; i++)
	  {
		  tableReference[i] = new LinkedList<KeyValuePair<Object, Object>>();
	  }
	  tableReference[containerIndexA].addFirst(new KeyValuePair<Object, Object>("a", 12));
	  tableReference[containerIndexE].addFirst(new KeyValuePair<Object, Object>("e", 10));

      assertEquals("wert", hashMap.remove("test"));
      LinkedList<KeyValuePair<Object, Object>>[] tableResult = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, tableResult);

      tableReference[containerIndexA].clear();
      assertEquals(12, hashMap.remove("a"));
      tableResult = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, tableResult);

      tableReference[containerIndexE].clear();
      assertEquals(10, hashMap.remove("e"));
      tableResult = (LinkedList<KeyValuePair<Object, Object>>[])tableField.get(hashMap);
      tablesEqual(tableReference, tableResult);
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  /////////////
  // Contains
  /////////////
  @SuppressWarnings("unchecked")
  @Test
  public void H04_MyListsHashMapContainsKeyTrue()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  table[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      table[hashFunction.apply(10)].add(new KeyValuePair<Object, Object>(10, 10));
      table[hashFunction.apply("test")].add(new KeyValuePair<Object, Object>("test", 123));
      table[hashFunction.apply("key")].add(new KeyValuePair<Object, Object>("key", "hallo"));

      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);
      tableField.set(hashMap, table);

      assertEquals(true, hashMap.containsKey("test"));
      assertEquals(true, hashMap.containsKey("key"));
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  @Test
  public void H04_MyListsHashMapContainsKeyFalse()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  table[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      table[hashFunction.apply("e")].add(new KeyValuePair<Object, Object>("e", 10));
      table[hashFunction.apply("testen")].add(new KeyValuePair<Object, Object>("testen", 123));
      table[hashFunction.apply(0)].add(new KeyValuePair<Object, Object>(0, "hallo"));

      assertEquals(false, hashMap.containsKey("test"));

      tableField.set(hashMap, table);

      assertEquals(false, hashMap.containsKey("key"));
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
  public void H04_MyIndexHoppingHashMapGetValuePositive()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  table[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      table[hashFunction.apply("e")].add(new KeyValuePair<Object, Object>("e", 10));
      table[hashFunction.apply("test")].add(new KeyValuePair<Object, Object>("test", 123));
      table[hashFunction.apply(0)].add(new KeyValuePair<Object, Object>(0, "hallo"));

      tableField.set(hashMap, table);

      assertEquals(10, hashMap.getValue("e"));
      assertEquals(123, hashMap.getValue("test"));
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  @Test
  public void H04_MyIndexHoppingHashMapGetValueNegative()
  {
    Hash2IndexFct<Object> hashFunction = new Hash2IndexFct<Object>(13, 0);
    MyListsHashMap<Object, Object> hashMap = new MyListsHashMap<Object, Object>(hashFunction);

    try
    {
      Field tableField = hashMap.getClass().getDeclaredField("table");
      tableField.setAccessible(true);

	  LinkedList<KeyValuePair<Object, Object>>[] table = (LinkedList<KeyValuePair<Object,Object>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());

      for (int i = 0; i < 13; i++)
      {
    	  table[i] = new LinkedList<KeyValuePair<Object, Object>>();
      }

      table[hashFunction.apply("e")].add(new KeyValuePair<Object, Object>("e", 10));
      table[hashFunction.apply("test")].add(new KeyValuePair<Object, Object>("test", 123));
      table[hashFunction.apply(0)].add(new KeyValuePair<Object, Object>(0, "hallo"));

      tableField.set(hashMap, table);

      assertEquals(null, hashMap.getValue("b"));
      assertEquals(null, hashMap.getValue("c"));
    }
    catch (Exception e)
    {
      fail(e.getMessage());
    }
  }

  private void tablesEqual(LinkedList<KeyValuePair<Object,Object>>[] referenceTable, LinkedList<KeyValuePair<Object,Object>>[] table)
  {
	  assertEquals(referenceTable.length, table.length, "Die Hashtabelle hat die Länge " + referenceTable.length + ", erwartet war: " + table.length);

	  for (int i = 0; i < table.length; i++)
	  {
		  if (referenceTable[i].size() > 0)
			  assertNotEquals(null, table[i], "Liste in Container " + i + " entspricht nicht den Erwartungen (ist null)");

		  assertEquals(referenceTable[i].size(), table[i].size());
		  for (int j = 0; j < table[i].size(); j++)
		  {
			  assertEquals(referenceTable[i].get(j).getKey(), table[i].get(j).getKey());
			  assertEquals(referenceTable[i].get(j).getValue(), table[i].get(j).getValue());
		  }
	  }
  }
}
