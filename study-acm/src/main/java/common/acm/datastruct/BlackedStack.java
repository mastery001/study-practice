package common.acm.datastruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackedStack<E> extends Stack<Character> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * 存储了括号对应的关系
	 */
	private static final Map<Character, Character> BLACKED_RELATION_MAP = new HashMap<Character, Character>() {
		/**
		*
		*/
		private static final long serialVersionUID = 1L;

		{
			put('(', ')');
			put('[', ']');
			put('{', '}');
		}
	};

	private Character currentitem;

	@Override
	public Character push(Character item) {
		currentitem = item;
		logger.info("current push item is " + item  + " , and the matcher is " + getCurrentLeftMatchBlacked());
		return super.push(currentitem);
	}

	/**
	 * 获得当前左侧括号的匹配值
	 * 
	 * @return
	 */
	public Character getCurrentLeftMatchBlacked() {
		return BLACKED_RELATION_MAP.get(currentitem);
	}

	@Override
	public synchronized Character pop() {
		Character c = super.pop();
		StringBuffer loggerBuffer = new StringBuffer("current pop item is " + c);
		if(!empty()) {
			currentitem = super.peek();
			loggerBuffer.append(", and the last item is not null , value is " + currentitem);
		}
		logger.info(loggerBuffer.toString());
		return c;
	}

}
