package i.design.modules.global.services


interface IGlobalConfigService {
    /**
     * 得到配置
     *
     * @param key String
     * @param defaultValue String
     * @return String
     */
    fun getConfig(key: String, defaultValue: String): String

    /**
     * 设置配置
     *
     * @param key String
     * @param value String
     * @return String
     */
    fun setConfig(key: String, value: String): String

}