package org.jeecg.common.util;

import cn.com.dgwl.tools.v2.encrypt.Md5;

/**
 * @Description: 密码工具类
 * @author: jeecg-boot
 */
public class PasswordUtil {
	public static String encrypt(String password, String salt) {
		return Md5.encode(Md5.encode(password) + '*' + salt);
	}
}
