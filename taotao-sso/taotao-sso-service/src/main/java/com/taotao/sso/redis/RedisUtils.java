package com.taotao.sso.redis;

public interface RedisUtils {

	/**
	 * set，保存数据
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value);

	/**
	 * set，保存数据，同时设置有效时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void set(String key, String value, int seconds);

	/**
	 * get，获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);

	/**
	 * delete,删除数据
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * expire,根据key，设置数据的有效时间，时间的单位是秒
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds);

	/**
	 * incr，利用redis的单线程特点，获取唯一数
	 * 
	 * @param key
	 * @return
	 */
	public long incr(String key);

}
