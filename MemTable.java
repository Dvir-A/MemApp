package mmn14_2;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class MemTable extends Hashtable<MemTime, String> {
	
	public MemTable() {
		super();
	}
	
	public  MemTable(MemTable mem) {
		super();
		if(mem!=null) {
			
			Iterator<MemTime> itr = mem.iterator();
			while(itr.hasNext()) {
				MemTime t = itr.next();
				this.put(t, mem.get(t));
			}
		}
	}


	public Iterator<MemTime> iterator() {
		Iterator<MemTime> iterator =new Iterator<MemTime>() {
			private Enumeration<MemTime> _enum= keys();
			@Override
			public void remove() {
				if(this.hasNext()) {
					this._enum.nextElement();
				}
			}
			
			@Override
			public MemTime next() {
				if(!hasNext()) {
					return null;
				}
				return this._enum.nextElement();
			}
			
			@Override
			public boolean hasNext() {
				return (_enum != null && this._enum.hasMoreElements());
			}
		};
		return iterator;
	}

}
