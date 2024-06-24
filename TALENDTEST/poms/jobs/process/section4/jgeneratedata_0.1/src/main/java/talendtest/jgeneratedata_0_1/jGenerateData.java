
package talendtest.jgeneratedata_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.MDM;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.SQLike;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: jGenerateData Purpose: <br>
 * Description: <br>
 * 
 * @author Frikha, Omar
 * @version 8.0.1.20240604_0759-patch
 * @status
 */
public class jGenerateData implements TalendJob {
	static {
		System.setProperty("TalendJob.log", "jGenerateData.log");
	}

	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger(jGenerateData.class);

	protected static void logIgnoredError(String message, Throwable cause) {
		log.error(message, cause);

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	protected java.util.Map<String, String> defaultProperties = new java.util.HashMap<String, String>();
	protected java.util.Map<String, String> additionalProperties = new java.util.HashMap<String, String>();

	public java.util.Map<String, String> getDefaultProperties() {
		return this.defaultProperties;
	}

	public java.util.Map<String, String> getAdditionalProperties() {
		return this.additionalProperties;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "jGenerateData";
	private final String projectName = "TALENDTEST";
	public Integer errorCode = null;
	private String currentComponent = "";
	public static boolean isStandaloneMS = Boolean.valueOf("false");

	private String cLabel = null;

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
			"_igL48DI5Ee-82ZYHS27FTw", "0.1");
	private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;

	private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;

		private String currentComponent = null;
		private String cLabel = null;

		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		private TalendException(Exception e, String errorComponent, String errorComponentLabel,
				final java.util.Map<String, Object> globalMap) {
			this(e, errorComponent, globalMap);
			this.cLabel = errorComponentLabel;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					jGenerateData.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(jGenerateData.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
						if (enableLogStash) {
							talendJobLog.addJobExceptionMessage(currentComponent, cLabel, null, e);
							talendJobLogProcess(globalMap);
						}
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void Implicit_Context_Database_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		Implicit_Context_Context_error(exception, errorComponent, globalMap);

	}

	public void Implicit_Context_Context_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		Implicit_Context_Database_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRowGenerator_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tReplicate_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputExcel_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputJSON_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRowGenerator_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void talendJobLog_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		talendJobLog_onSubJobError(exception, errorComponent, globalMap);
	}

	public void Implicit_Context_Database_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRowGenerator_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row_Implicit_Context_DatabaseStruct
			implements routines.system.IPersistableRow<row_Implicit_Context_DatabaseStruct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public String key;

		public String getKey() {
			return this.key;
		}

		public Boolean keyIsNullable() {
			return true;
		}

		public Boolean keyIsKey() {
			return false;
		}

		public Integer keyLength() {
			return 255;
		}

		public Integer keyPrecision() {
			return 0;
		}

		public String keyDefault() {

			return "";

		}

		public String keyComment() {

			return null;

		}

		public String keyPattern() {

			return null;

		}

		public String keyOriginalDbColumnName() {

			return "key";

		}

		public String value;

		public String getValue() {
			return this.value;
		}

		public Boolean valueIsNullable() {
			return true;
		}

		public Boolean valueIsKey() {
			return false;
		}

		public Integer valueLength() {
			return 255;
		}

		public Integer valuePrecision() {
			return 0;
		}

		public String valueDefault() {

			return "";

		}

		public String valueComment() {

			return null;

		}

		public String valuePattern() {

			return null;

		}

		public String valueOriginalDbColumnName() {

			return "value";

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.key = readString(dis);

					this.value = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.key = readString(dis);

					this.value = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.key, dos);

				// String

				writeString(this.value, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.key, dos);

				// String

				writeString(this.value, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("key=" + key);
			sb.append(",value=" + value);
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (key == null) {
				sb.append("<null>");
			} else {
				sb.append(key);
			}

			sb.append("|");

			if (value == null) {
				sb.append("<null>");
			} else {
				sb.append(value);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row_Implicit_Context_DatabaseStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void Implicit_Context_DatabaseProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("Implicit_Context_Database_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "Implicit_Context_Database");
		org.slf4j.MDC.put("_subJobPid", "9KP4Sf_" + subJobPidCounter.getAndIncrement());

		String currentVirtualComponent = null;

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row_Implicit_Context_DatabaseStruct row_Implicit_Context_Database = new row_Implicit_Context_DatabaseStruct();

				/**
				 * [Implicit_Context_Context begin ] start
				 */

				ok_Hash.put("Implicit_Context_Context", false);
				start_Hash.put("Implicit_Context_Context", System.currentTimeMillis());

				currentVirtualComponent = "Implicit_Context_Context";

				currentComponent = "Implicit_Context_Context";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "Main");

				int tos_count_Implicit_Context_Context = 0;

				if (log.isDebugEnabled())
					log.debug("Implicit_Context_Context - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_Implicit_Context_Context {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_Implicit_Context_Context = new StringBuilder();
							log4jParamters_Implicit_Context_Context.append("Parameters:");
							log4jParamters_Implicit_Context_Context.append("LOAD_NEW_VARIABLE" + " = " + "Warning");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("NOT_LOAD_OLD_VARIABLE" + " = " + "Warning");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("PRINT_OPERATIONS" + " = " + "true");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("DISABLE_ERROR" + " = " + "false");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("DISABLE_WARNINGS" + " = " + "true");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("DISABLE_INFO" + " = " + "true");
							log4jParamters_Implicit_Context_Context.append(" | ");
							log4jParamters_Implicit_Context_Context.append("DIEONERROR" + " = " + "false");
							log4jParamters_Implicit_Context_Context.append(" | ");
							if (log.isDebugEnabled())
								log.debug("Implicit_Context_Context - " + (log4jParamters_Implicit_Context_Context));
						}
					}
					new BytesLimit65535_Implicit_Context_Context().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("Implicit_Context_Context", "null_Context", "tContextLoad");
					talendJobLogProcess(globalMap);
				}

				java.util.List<String> assignList_Implicit_Context_Context = new java.util.ArrayList<String>();
				java.util.List<String> newPropertyList_Implicit_Context_Context = new java.util.ArrayList<String>();
				java.util.List<String> noAssignList_Implicit_Context_Context = new java.util.ArrayList<String>();
				int nb_line_Implicit_Context_Context = 0;

				/**
				 * [Implicit_Context_Context begin ] stop
				 */

				/**
				 * [Implicit_Context_Database begin ] start
				 */

				ok_Hash.put("Implicit_Context_Database", false);
				start_Hash.put("Implicit_Context_Database", System.currentTimeMillis());

				currentVirtualComponent = "Implicit_Context_Database";

				currentComponent = "Implicit_Context_Database";

				int tos_count_Implicit_Context_Database = 0;

				if (log.isDebugEnabled())
					log.debug("Implicit_Context_Database - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_Implicit_Context_Database {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_Implicit_Context_Database = new StringBuilder();
							log4jParamters_Implicit_Context_Database.append("Parameters:");
							log4jParamters_Implicit_Context_Database
									.append("USE_EXISTING_CONNECTION" + " = " + "false");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("DB_VERSION" + " = " + "V9_X");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("HOST" + " = " + "\"localhost\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("PORT" + " = " + "\"5433\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("DBNAME" + " = " + "\"formation_talend\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("SCHEMA_DB" + " = " + "\"CONFIG\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("USER" + " = " + "\"postgres\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("PASS" + " = " + String.valueOf(
									"enc:routine.encryption.key.v1:JDz0ZgS9OUnei083wcBTlT1JkE+POY2FX0rb1bdk8sI=")
									.substring(0, 4) + "...");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("QUERYSTORE" + " = " + "\"\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("QUERY" + " = "
									+ "\"SELECT    \\\"CONFIG\\\".\\\"VARIABLES\\\".\\\"key\\\",    \\\"CONFIG\\\".\\\"VARIABLES\\\".\\\"value\\\" FROM \\\"CONFIG\\\".\\\"VARIABLES\\\"\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database
									.append("SPECIFY_DATASOURCE_ALIAS" + " = " + "false");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("PROPERTIES" + " = " + "\"\"");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("USE_CURSOR" + " = " + "false");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("TRIM_ALL_COLUMN" + " = " + "false");
							log4jParamters_Implicit_Context_Database.append(" | ");
							log4jParamters_Implicit_Context_Database.append("TRIM_COLUMN" + " = " + "[]");
							log4jParamters_Implicit_Context_Database.append(" | ");
							if (log.isDebugEnabled())
								log.debug("Implicit_Context_Database - " + (log4jParamters_Implicit_Context_Database));
						}
					}
					new BytesLimit65535_Implicit_Context_Database().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("Implicit_Context_Database", "null_Database", "tPostgresqlInput");
					talendJobLogProcess(globalMap);
				}

				int nb_line_Implicit_Context_Database = 0;
				java.sql.Connection conn_Implicit_Context_Database = null;
				String driverClass_Implicit_Context_Database = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_Implicit_Context_Database = java.lang.Class
						.forName(driverClass_Implicit_Context_Database);
				String dbUser_Implicit_Context_Database = "postgres";

				final String decryptedPassword_Implicit_Context_Database = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:dQiDZZPnkjGcVLFYfuRvJ4lHdjD1IPKgjCnfBmITZYw=");

				String dbPwd_Implicit_Context_Database = decryptedPassword_Implicit_Context_Database;

				String url_Implicit_Context_Database = "jdbc:postgresql://" + "localhost" + ":" + "5433" + "/"
						+ "formation_talend";

				log.debug(
						"Implicit_Context_Database - Driver ClassName: " + driverClass_Implicit_Context_Database + ".");

				log.debug("Implicit_Context_Database - Connection attempt to '" + url_Implicit_Context_Database
						+ "' with the username '" + dbUser_Implicit_Context_Database + "'.");

				conn_Implicit_Context_Database = java.sql.DriverManager.getConnection(url_Implicit_Context_Database,
						dbUser_Implicit_Context_Database, dbPwd_Implicit_Context_Database);
				log.debug("Implicit_Context_Database - Connection to '" + url_Implicit_Context_Database
						+ "' has succeeded.");

				log.debug("Implicit_Context_Database - Connection is set auto commit to 'false'.");

				conn_Implicit_Context_Database.setAutoCommit(false);

				java.sql.Statement stmt_Implicit_Context_Database = conn_Implicit_Context_Database.createStatement();

				String dbquery_Implicit_Context_Database = "SELECT \n  \"CONFIG\".\"VARIABLES\".\"key\", \n  \"CONFIG\".\"VARIABLES\".\"value\"\nFROM \"CONFIG\".\"VARIABLES\"";

				log.debug("Implicit_Context_Database - Executing the query: '" + dbquery_Implicit_Context_Database
						+ "'.");

				globalMap.put("Implicit_Context_Database_QUERY", dbquery_Implicit_Context_Database);

				java.sql.ResultSet rs_Implicit_Context_Database = null;

				try {
					rs_Implicit_Context_Database = stmt_Implicit_Context_Database
							.executeQuery(dbquery_Implicit_Context_Database);
					java.sql.ResultSetMetaData rsmd_Implicit_Context_Database = rs_Implicit_Context_Database
							.getMetaData();
					int colQtyInRs_Implicit_Context_Database = rsmd_Implicit_Context_Database.getColumnCount();

					String tmpContent_Implicit_Context_Database = null;

					log.debug("Implicit_Context_Database - Retrieving records from the database.");

					while (rs_Implicit_Context_Database.next()) {
						nb_line_Implicit_Context_Database++;

						if (colQtyInRs_Implicit_Context_Database < 1) {
							row_Implicit_Context_Database.key = null;
						} else {

							row_Implicit_Context_Database.key = routines.system.JDBCUtil
									.getString(rs_Implicit_Context_Database, 1, false);
						}
						if (colQtyInRs_Implicit_Context_Database < 2) {
							row_Implicit_Context_Database.value = null;
						} else {

							row_Implicit_Context_Database.value = routines.system.JDBCUtil
									.getString(rs_Implicit_Context_Database, 2, false);
						}

						log.debug("Implicit_Context_Database - Retrieving the record "
								+ nb_line_Implicit_Context_Database + ".");

						/**
						 * [Implicit_Context_Database begin ] stop
						 */

						/**
						 * [Implicit_Context_Database main ] start
						 */

						currentVirtualComponent = "Implicit_Context_Database";

						currentComponent = "Implicit_Context_Database";

						tos_count_Implicit_Context_Database++;

						/**
						 * [Implicit_Context_Database main ] stop
						 */

						/**
						 * [Implicit_Context_Database process_data_begin ] start
						 */

						currentVirtualComponent = "Implicit_Context_Database";

						currentComponent = "Implicit_Context_Database";

						/**
						 * [Implicit_Context_Database process_data_begin ] stop
						 */

						/**
						 * [Implicit_Context_Context main ] start
						 */

						currentVirtualComponent = "Implicit_Context_Context";

						currentComponent = "Implicit_Context_Context";

						if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

								, "Main", "Implicit_Context_Database", "null_Database", "tPostgresqlInput",
								"Implicit_Context_Context", "null_Context", "tContextLoad"

						)) {
							talendJobLogProcess(globalMap);
						}

						//////////////////////////
						String tmp_key_Implicit_Context_Context = null;
						String key_Implicit_Context_Context = null;
						if (row_Implicit_Context_Database.key != null) {
							tmp_key_Implicit_Context_Context = row_Implicit_Context_Database.key.trim();
							if ((tmp_key_Implicit_Context_Context.startsWith("#")
									|| tmp_key_Implicit_Context_Context.startsWith("!"))) {
								tmp_key_Implicit_Context_Context = null;
							} else {
								row_Implicit_Context_Database.key = tmp_key_Implicit_Context_Context;
							}
						}
						if (row_Implicit_Context_Database.key != null) {
							key_Implicit_Context_Context = row_Implicit_Context_Database.key;
						}
						String value_Implicit_Context_Context = null;
						if (row_Implicit_Context_Database.value != null) {
							value_Implicit_Context_Context = row_Implicit_Context_Database.value;
						}

						String currentValue_Implicit_Context_Context = value_Implicit_Context_Context;

						System.out.println("Implicit_Context_Context set key \"" + key_Implicit_Context_Context
								+ "\" with value \"" + currentValue_Implicit_Context_Context + "\"");
						if (tmp_key_Implicit_Context_Context != null) {
							try {

								if (context.getProperty(key_Implicit_Context_Context) != null) {
									assignList_Implicit_Context_Context.add(key_Implicit_Context_Context);
								} else {
									newPropertyList_Implicit_Context_Context.add(key_Implicit_Context_Context);
								}
								if (value_Implicit_Context_Context == null) {
									context.setProperty(key_Implicit_Context_Context, "");
								} else {
									context.setProperty(key_Implicit_Context_Context, value_Implicit_Context_Context);
								}
							} catch (java.lang.Exception e) {
								globalMap.put("Implicit_Context_Context_ERROR_MESSAGE", e.getMessage());
								log.error("Implicit_Context_Context - Setting a value for the key \""
										+ key_Implicit_Context_Context + "\" has failed. Error message: "
										+ e.getMessage());
								System.err.println("Setting a value for the key \"" + key_Implicit_Context_Context
										+ "\" has failed. Error message: " + e.getMessage());
							}
							nb_line_Implicit_Context_Context++;
						}
						//////////////////////////

						tos_count_Implicit_Context_Context++;

						/**
						 * [Implicit_Context_Context main ] stop
						 */

						/**
						 * [Implicit_Context_Context process_data_begin ] start
						 */

						currentVirtualComponent = "Implicit_Context_Context";

						currentComponent = "Implicit_Context_Context";

						/**
						 * [Implicit_Context_Context process_data_begin ] stop
						 */

						/**
						 * [Implicit_Context_Context process_data_end ] start
						 */

						currentVirtualComponent = "Implicit_Context_Context";

						currentComponent = "Implicit_Context_Context";

						/**
						 * [Implicit_Context_Context process_data_end ] stop
						 */

						/**
						 * [Implicit_Context_Database process_data_end ] start
						 */

						currentVirtualComponent = "Implicit_Context_Database";

						currentComponent = "Implicit_Context_Database";

						/**
						 * [Implicit_Context_Database process_data_end ] stop
						 */

						/**
						 * [Implicit_Context_Database end ] start
						 */

						currentVirtualComponent = "Implicit_Context_Database";

						currentComponent = "Implicit_Context_Database";

					}
				} finally {
					if (rs_Implicit_Context_Database != null) {
						rs_Implicit_Context_Database.close();
					}
					if (stmt_Implicit_Context_Database != null) {
						stmt_Implicit_Context_Database.close();
					}
					if (conn_Implicit_Context_Database != null && !conn_Implicit_Context_Database.isClosed()) {

						log.debug("Implicit_Context_Database - Closing the connection to the database.");

						conn_Implicit_Context_Database.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

						log.debug("Implicit_Context_Database - Connection to the database closed.");

					}

				}
				globalMap.put("Implicit_Context_Database_NB_LINE", nb_line_Implicit_Context_Database);
				log.debug("Implicit_Context_Database - Retrieved records count: " + nb_line_Implicit_Context_Database
						+ " .");

				if (log.isDebugEnabled())
					log.debug("Implicit_Context_Database - " + ("Done."));

				ok_Hash.put("Implicit_Context_Database", true);
				end_Hash.put("Implicit_Context_Database", System.currentTimeMillis());

				/**
				 * [Implicit_Context_Database end ] stop
				 */

				/**
				 * [Implicit_Context_Context end ] start
				 */

				currentVirtualComponent = "Implicit_Context_Context";

				currentComponent = "Implicit_Context_Context";

				java.util.Enumeration<?> enu_Implicit_Context_Context = context.propertyNames();
				while (enu_Implicit_Context_Context.hasMoreElements()) {
					String key_Implicit_Context_Context = (String) enu_Implicit_Context_Context.nextElement();
					if (!assignList_Implicit_Context_Context.contains(key_Implicit_Context_Context)
							&& !newPropertyList_Implicit_Context_Context.contains(key_Implicit_Context_Context)) {
						noAssignList_Implicit_Context_Context.add(key_Implicit_Context_Context);
					}
				}

				String newPropertyStr_Implicit_Context_Context = newPropertyList_Implicit_Context_Context.toString();
				String newProperty_Implicit_Context_Context = newPropertyStr_Implicit_Context_Context.substring(1,
						newPropertyStr_Implicit_Context_Context.length() - 1);

				String noAssignStr_Implicit_Context_Context = noAssignList_Implicit_Context_Context.toString();
				String noAssign_Implicit_Context_Context = noAssignStr_Implicit_Context_Context.substring(1,
						noAssignStr_Implicit_Context_Context.length() - 1);

				globalMap.put("Implicit_Context_Context_KEY_NOT_INCONTEXT", newProperty_Implicit_Context_Context);
				globalMap.put("Implicit_Context_Context_KEY_NOT_LOADED", noAssign_Implicit_Context_Context);

				globalMap.put("Implicit_Context_Context_NB_LINE", nb_line_Implicit_Context_Context);

				List<String> parametersToEncrypt_Implicit_Context_Context = new java.util.ArrayList<String>();

				resumeUtil.addLog("NODE", "NODE:Implicit_Context_Context", "", Thread.currentThread().getId() + "", "",
						"", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class,
								parametersToEncrypt_Implicit_Context_Context));
				log.info("Implicit_Context_Context - Loaded contexts count: " + nb_line_Implicit_Context_Context + ".");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "Main", 2, 0,
						"Implicit_Context_Database", "null_Database", "tPostgresqlInput", "Implicit_Context_Context",
						"null_Context", "tContextLoad", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("Implicit_Context_Context - " + ("Done."));

				ok_Hash.put("Implicit_Context_Context", true);
				end_Hash.put("Implicit_Context_Context", System.currentTimeMillis());

				/**
				 * [Implicit_Context_Context end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			te.setVirtualComponentName(currentVirtualComponent);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [Implicit_Context_Database finally ] start
				 */

				currentVirtualComponent = "Implicit_Context_Database";

				currentComponent = "Implicit_Context_Database";

				/**
				 * [Implicit_Context_Database finally ] stop
				 */

				/**
				 * [Implicit_Context_Context finally ] start
				 */

				currentVirtualComponent = "Implicit_Context_Context";

				currentComponent = "Implicit_Context_Context";

				/**
				 * [Implicit_Context_Context finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("Implicit_Context_Database_SUBPROCESS_STATE", 1);
	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public Integer IDENTIFIANT;

		public Integer getIDENTIFIANT() {
			return this.IDENTIFIANT;
		}

		public Boolean IDENTIFIANTIsNullable() {
			return true;
		}

		public Boolean IDENTIFIANTIsKey() {
			return false;
		}

		public Integer IDENTIFIANTLength() {
			return null;
		}

		public Integer IDENTIFIANTPrecision() {
			return null;
		}

		public String IDENTIFIANTDefault() {

			return "";

		}

		public String IDENTIFIANTComment() {

			return "";

		}

		public String IDENTIFIANTPattern() {

			return "";

		}

		public String IDENTIFIANTOriginalDbColumnName() {

			return "IDENTIFIANT";

		}

		public String NOM;

		public String getNOM() {
			return this.NOM;
		}

		public Boolean NOMIsNullable() {
			return true;
		}

		public Boolean NOMIsKey() {
			return false;
		}

		public Integer NOMLength() {
			return null;
		}

		public Integer NOMPrecision() {
			return null;
		}

		public String NOMDefault() {

			return "";

		}

		public String NOMComment() {

			return "";

		}

		public String NOMPattern() {

			return "";

		}

		public String NOMOriginalDbColumnName() {

			return "NOM";

		}

		public String PRENOM;

		public String getPRENOM() {
			return this.PRENOM;
		}

		public Boolean PRENOMIsNullable() {
			return true;
		}

		public Boolean PRENOMIsKey() {
			return false;
		}

		public Integer PRENOMLength() {
			return null;
		}

		public Integer PRENOMPrecision() {
			return null;
		}

		public String PRENOMDefault() {

			return "";

		}

		public String PRENOMComment() {

			return "";

		}

		public String PRENOMPattern() {

			return "";

		}

		public String PRENOMOriginalDbColumnName() {

			return "PRENOM";

		}

		public String VILLE;

		public String getVILLE() {
			return this.VILLE;
		}

		public Boolean VILLEIsNullable() {
			return true;
		}

		public Boolean VILLEIsKey() {
			return false;
		}

		public Integer VILLELength() {
			return null;
		}

		public Integer VILLEPrecision() {
			return null;
		}

		public String VILLEDefault() {

			return "";

		}

		public String VILLEComment() {

			return "";

		}

		public String VILLEPattern() {

			return "";

		}

		public String VILLEOriginalDbColumnName() {

			return "VILLE";

		}

		public String ETAT;

		public String getETAT() {
			return this.ETAT;
		}

		public Boolean ETATIsNullable() {
			return true;
		}

		public Boolean ETATIsKey() {
			return false;
		}

		public Integer ETATLength() {
			return null;
		}

		public Integer ETATPrecision() {
			return null;
		}

		public String ETATDefault() {

			return "";

		}

		public String ETATComment() {

			return "";

		}

		public String ETATPattern() {

			return "";

		}

		public String ETATOriginalDbColumnName() {

			return "ETAT";

		}

		public Integer SALAIRE;

		public Integer getSALAIRE() {
			return this.SALAIRE;
		}

		public Boolean SALAIREIsNullable() {
			return true;
		}

		public Boolean SALAIREIsKey() {
			return false;
		}

		public Integer SALAIRELength() {
			return null;
		}

		public Integer SALAIREPrecision() {
			return null;
		}

		public String SALAIREDefault() {

			return "";

		}

		public String SALAIREComment() {

			return "";

		}

		public String SALAIREPattern() {

			return "";

		}

		public String SALAIREOriginalDbColumnName() {

			return "SALAIRE";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("IDENTIFIANT=" + String.valueOf(IDENTIFIANT));
			sb.append(",NOM=" + NOM);
			sb.append(",PRENOM=" + PRENOM);
			sb.append(",VILLE=" + VILLE);
			sb.append(",ETAT=" + ETAT);
			sb.append(",SALAIRE=" + String.valueOf(SALAIRE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (IDENTIFIANT == null) {
				sb.append("<null>");
			} else {
				sb.append(IDENTIFIANT);
			}

			sb.append("|");

			if (NOM == null) {
				sb.append("<null>");
			} else {
				sb.append(NOM);
			}

			sb.append("|");

			if (PRENOM == null) {
				sb.append("<null>");
			} else {
				sb.append(PRENOM);
			}

			sb.append("|");

			if (VILLE == null) {
				sb.append("<null>");
			} else {
				sb.append(VILLE);
			}

			sb.append("|");

			if (ETAT == null) {
				sb.append("<null>");
			} else {
				sb.append(ETAT);
			}

			sb.append("|");

			if (SALAIRE == null) {
				sb.append("<null>");
			} else {
				sb.append(SALAIRE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public Integer IDENTIFIANT;

		public Integer getIDENTIFIANT() {
			return this.IDENTIFIANT;
		}

		public Boolean IDENTIFIANTIsNullable() {
			return true;
		}

		public Boolean IDENTIFIANTIsKey() {
			return false;
		}

		public Integer IDENTIFIANTLength() {
			return null;
		}

		public Integer IDENTIFIANTPrecision() {
			return null;
		}

		public String IDENTIFIANTDefault() {

			return "";

		}

		public String IDENTIFIANTComment() {

			return "";

		}

		public String IDENTIFIANTPattern() {

			return "";

		}

		public String IDENTIFIANTOriginalDbColumnName() {

			return "IDENTIFIANT";

		}

		public String NOM;

		public String getNOM() {
			return this.NOM;
		}

		public Boolean NOMIsNullable() {
			return true;
		}

		public Boolean NOMIsKey() {
			return false;
		}

		public Integer NOMLength() {
			return null;
		}

		public Integer NOMPrecision() {
			return null;
		}

		public String NOMDefault() {

			return "";

		}

		public String NOMComment() {

			return "";

		}

		public String NOMPattern() {

			return "";

		}

		public String NOMOriginalDbColumnName() {

			return "NOM";

		}

		public String PRENOM;

		public String getPRENOM() {
			return this.PRENOM;
		}

		public Boolean PRENOMIsNullable() {
			return true;
		}

		public Boolean PRENOMIsKey() {
			return false;
		}

		public Integer PRENOMLength() {
			return null;
		}

		public Integer PRENOMPrecision() {
			return null;
		}

		public String PRENOMDefault() {

			return "";

		}

		public String PRENOMComment() {

			return "";

		}

		public String PRENOMPattern() {

			return "";

		}

		public String PRENOMOriginalDbColumnName() {

			return "PRENOM";

		}

		public String VILLE;

		public String getVILLE() {
			return this.VILLE;
		}

		public Boolean VILLEIsNullable() {
			return true;
		}

		public Boolean VILLEIsKey() {
			return false;
		}

		public Integer VILLELength() {
			return null;
		}

		public Integer VILLEPrecision() {
			return null;
		}

		public String VILLEDefault() {

			return "";

		}

		public String VILLEComment() {

			return "";

		}

		public String VILLEPattern() {

			return "";

		}

		public String VILLEOriginalDbColumnName() {

			return "VILLE";

		}

		public String ETAT;

		public String getETAT() {
			return this.ETAT;
		}

		public Boolean ETATIsNullable() {
			return true;
		}

		public Boolean ETATIsKey() {
			return false;
		}

		public Integer ETATLength() {
			return null;
		}

		public Integer ETATPrecision() {
			return null;
		}

		public String ETATDefault() {

			return "";

		}

		public String ETATComment() {

			return "";

		}

		public String ETATPattern() {

			return "";

		}

		public String ETATOriginalDbColumnName() {

			return "ETAT";

		}

		public Integer SALAIRE;

		public Integer getSALAIRE() {
			return this.SALAIRE;
		}

		public Boolean SALAIREIsNullable() {
			return true;
		}

		public Boolean SALAIREIsKey() {
			return false;
		}

		public Integer SALAIRELength() {
			return null;
		}

		public Integer SALAIREPrecision() {
			return null;
		}

		public String SALAIREDefault() {

			return "";

		}

		public String SALAIREComment() {

			return "";

		}

		public String SALAIREPattern() {

			return "";

		}

		public String SALAIREOriginalDbColumnName() {

			return "SALAIRE";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("IDENTIFIANT=" + String.valueOf(IDENTIFIANT));
			sb.append(",NOM=" + NOM);
			sb.append(",PRENOM=" + PRENOM);
			sb.append(",VILLE=" + VILLE);
			sb.append(",ETAT=" + ETAT);
			sb.append(",SALAIRE=" + String.valueOf(SALAIRE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (IDENTIFIANT == null) {
				sb.append("<null>");
			} else {
				sb.append(IDENTIFIANT);
			}

			sb.append("|");

			if (NOM == null) {
				sb.append("<null>");
			} else {
				sb.append(NOM);
			}

			sb.append("|");

			if (PRENOM == null) {
				sb.append("<null>");
			} else {
				sb.append(PRENOM);
			}

			sb.append("|");

			if (VILLE == null) {
				sb.append("<null>");
			} else {
				sb.append(VILLE);
			}

			sb.append("|");

			if (ETAT == null) {
				sb.append("<null>");
			} else {
				sb.append(ETAT);
			}

			sb.append("|");

			if (SALAIRE == null) {
				sb.append("<null>");
			} else {
				sb.append(SALAIRE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public Integer IDENTIFIANT;

		public Integer getIDENTIFIANT() {
			return this.IDENTIFIANT;
		}

		public Boolean IDENTIFIANTIsNullable() {
			return true;
		}

		public Boolean IDENTIFIANTIsKey() {
			return false;
		}

		public Integer IDENTIFIANTLength() {
			return null;
		}

		public Integer IDENTIFIANTPrecision() {
			return null;
		}

		public String IDENTIFIANTDefault() {

			return "";

		}

		public String IDENTIFIANTComment() {

			return "";

		}

		public String IDENTIFIANTPattern() {

			return "";

		}

		public String IDENTIFIANTOriginalDbColumnName() {

			return "IDENTIFIANT";

		}

		public String NOM;

		public String getNOM() {
			return this.NOM;
		}

		public Boolean NOMIsNullable() {
			return true;
		}

		public Boolean NOMIsKey() {
			return false;
		}

		public Integer NOMLength() {
			return null;
		}

		public Integer NOMPrecision() {
			return null;
		}

		public String NOMDefault() {

			return "";

		}

		public String NOMComment() {

			return "";

		}

		public String NOMPattern() {

			return "";

		}

		public String NOMOriginalDbColumnName() {

			return "NOM";

		}

		public String PRENOM;

		public String getPRENOM() {
			return this.PRENOM;
		}

		public Boolean PRENOMIsNullable() {
			return true;
		}

		public Boolean PRENOMIsKey() {
			return false;
		}

		public Integer PRENOMLength() {
			return null;
		}

		public Integer PRENOMPrecision() {
			return null;
		}

		public String PRENOMDefault() {

			return "";

		}

		public String PRENOMComment() {

			return "";

		}

		public String PRENOMPattern() {

			return "";

		}

		public String PRENOMOriginalDbColumnName() {

			return "PRENOM";

		}

		public String VILLE;

		public String getVILLE() {
			return this.VILLE;
		}

		public Boolean VILLEIsNullable() {
			return true;
		}

		public Boolean VILLEIsKey() {
			return false;
		}

		public Integer VILLELength() {
			return null;
		}

		public Integer VILLEPrecision() {
			return null;
		}

		public String VILLEDefault() {

			return "";

		}

		public String VILLEComment() {

			return "";

		}

		public String VILLEPattern() {

			return "";

		}

		public String VILLEOriginalDbColumnName() {

			return "VILLE";

		}

		public String ETAT;

		public String getETAT() {
			return this.ETAT;
		}

		public Boolean ETATIsNullable() {
			return true;
		}

		public Boolean ETATIsKey() {
			return false;
		}

		public Integer ETATLength() {
			return null;
		}

		public Integer ETATPrecision() {
			return null;
		}

		public String ETATDefault() {

			return "";

		}

		public String ETATComment() {

			return "";

		}

		public String ETATPattern() {

			return "";

		}

		public String ETATOriginalDbColumnName() {

			return "ETAT";

		}

		public Integer SALAIRE;

		public Integer getSALAIRE() {
			return this.SALAIRE;
		}

		public Boolean SALAIREIsNullable() {
			return true;
		}

		public Boolean SALAIREIsKey() {
			return false;
		}

		public Integer SALAIRELength() {
			return null;
		}

		public Integer SALAIREPrecision() {
			return null;
		}

		public String SALAIREDefault() {

			return "";

		}

		public String SALAIREComment() {

			return "";

		}

		public String SALAIREPattern() {

			return "";

		}

		public String SALAIREOriginalDbColumnName() {

			return "SALAIRE";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("IDENTIFIANT=" + String.valueOf(IDENTIFIANT));
			sb.append(",NOM=" + NOM);
			sb.append(",PRENOM=" + PRENOM);
			sb.append(",VILLE=" + VILLE);
			sb.append(",ETAT=" + ETAT);
			sb.append(",SALAIRE=" + String.valueOf(SALAIRE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (IDENTIFIANT == null) {
				sb.append("<null>");
			} else {
				sb.append(IDENTIFIANT);
			}

			sb.append("|");

			if (NOM == null) {
				sb.append("<null>");
			} else {
				sb.append(NOM);
			}

			sb.append("|");

			if (PRENOM == null) {
				sb.append("<null>");
			} else {
				sb.append(PRENOM);
			}

			sb.append("|");

			if (VILLE == null) {
				sb.append("<null>");
			} else {
				sb.append(VILLE);
			}

			sb.append("|");

			if (ETAT == null) {
				sb.append("<null>");
			} else {
				sb.append(ETAT);
			}

			sb.append("|");

			if (SALAIRE == null) {
				sb.append("<null>");
			} else {
				sb.append(SALAIRE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public Integer IDENTIFIANT;

		public Integer getIDENTIFIANT() {
			return this.IDENTIFIANT;
		}

		public Boolean IDENTIFIANTIsNullable() {
			return true;
		}

		public Boolean IDENTIFIANTIsKey() {
			return false;
		}

		public Integer IDENTIFIANTLength() {
			return null;
		}

		public Integer IDENTIFIANTPrecision() {
			return null;
		}

		public String IDENTIFIANTDefault() {

			return "";

		}

		public String IDENTIFIANTComment() {

			return "";

		}

		public String IDENTIFIANTPattern() {

			return "";

		}

		public String IDENTIFIANTOriginalDbColumnName() {

			return "IDENTIFIANT";

		}

		public String NOM;

		public String getNOM() {
			return this.NOM;
		}

		public Boolean NOMIsNullable() {
			return true;
		}

		public Boolean NOMIsKey() {
			return false;
		}

		public Integer NOMLength() {
			return null;
		}

		public Integer NOMPrecision() {
			return null;
		}

		public String NOMDefault() {

			return "";

		}

		public String NOMComment() {

			return "";

		}

		public String NOMPattern() {

			return "";

		}

		public String NOMOriginalDbColumnName() {

			return "NOM";

		}

		public String PRENOM;

		public String getPRENOM() {
			return this.PRENOM;
		}

		public Boolean PRENOMIsNullable() {
			return true;
		}

		public Boolean PRENOMIsKey() {
			return false;
		}

		public Integer PRENOMLength() {
			return null;
		}

		public Integer PRENOMPrecision() {
			return null;
		}

		public String PRENOMDefault() {

			return "";

		}

		public String PRENOMComment() {

			return "";

		}

		public String PRENOMPattern() {

			return "";

		}

		public String PRENOMOriginalDbColumnName() {

			return "PRENOM";

		}

		public String VILLE;

		public String getVILLE() {
			return this.VILLE;
		}

		public Boolean VILLEIsNullable() {
			return true;
		}

		public Boolean VILLEIsKey() {
			return false;
		}

		public Integer VILLELength() {
			return null;
		}

		public Integer VILLEPrecision() {
			return null;
		}

		public String VILLEDefault() {

			return "";

		}

		public String VILLEComment() {

			return "";

		}

		public String VILLEPattern() {

			return "";

		}

		public String VILLEOriginalDbColumnName() {

			return "VILLE";

		}

		public String ETAT;

		public String getETAT() {
			return this.ETAT;
		}

		public Boolean ETATIsNullable() {
			return true;
		}

		public Boolean ETATIsKey() {
			return false;
		}

		public Integer ETATLength() {
			return null;
		}

		public Integer ETATPrecision() {
			return null;
		}

		public String ETATDefault() {

			return "";

		}

		public String ETATComment() {

			return "";

		}

		public String ETATPattern() {

			return "";

		}

		public String ETATOriginalDbColumnName() {

			return "ETAT";

		}

		public Integer SALAIRE;

		public Integer getSALAIRE() {
			return this.SALAIRE;
		}

		public Boolean SALAIREIsNullable() {
			return true;
		}

		public Boolean SALAIREIsKey() {
			return false;
		}

		public Integer SALAIRELength() {
			return null;
		}

		public Integer SALAIREPrecision() {
			return null;
		}

		public String SALAIREDefault() {

			return "";

		}

		public String SALAIREComment() {

			return "";

		}

		public String SALAIREPattern() {

			return "";

		}

		public String SALAIREOriginalDbColumnName() {

			return "SALAIRE";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("IDENTIFIANT=" + String.valueOf(IDENTIFIANT));
			sb.append(",NOM=" + NOM);
			sb.append(",PRENOM=" + PRENOM);
			sb.append(",VILLE=" + VILLE);
			sb.append(",ETAT=" + ETAT);
			sb.append(",SALAIRE=" + String.valueOf(SALAIRE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (IDENTIFIANT == null) {
				sb.append("<null>");
			} else {
				sb.append(IDENTIFIANT);
			}

			sb.append("|");

			if (NOM == null) {
				sb.append("<null>");
			} else {
				sb.append(NOM);
			}

			sb.append("|");

			if (PRENOM == null) {
				sb.append("<null>");
			} else {
				sb.append(PRENOM);
			}

			sb.append("|");

			if (VILLE == null) {
				sb.append("<null>");
			} else {
				sb.append(VILLE);
			}

			sb.append("|");

			if (ETAT == null) {
				sb.append("<null>");
			} else {
				sb.append(ETAT);
			}

			sb.append("|");

			if (SALAIRE == null) {
				sb.append("<null>");
			} else {
				sb.append(SALAIRE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_TALENDTEST_jGenerateData = new byte[0];
		static byte[] commonByteArray_TALENDTEST_jGenerateData = new byte[0];

		public Integer IDENTIFIANT;

		public Integer getIDENTIFIANT() {
			return this.IDENTIFIANT;
		}

		public Boolean IDENTIFIANTIsNullable() {
			return true;
		}

		public Boolean IDENTIFIANTIsKey() {
			return false;
		}

		public Integer IDENTIFIANTLength() {
			return null;
		}

		public Integer IDENTIFIANTPrecision() {
			return null;
		}

		public String IDENTIFIANTDefault() {

			return "";

		}

		public String IDENTIFIANTComment() {

			return "";

		}

		public String IDENTIFIANTPattern() {

			return "";

		}

		public String IDENTIFIANTOriginalDbColumnName() {

			return "IDENTIFIANT";

		}

		public String NOM;

		public String getNOM() {
			return this.NOM;
		}

		public Boolean NOMIsNullable() {
			return true;
		}

		public Boolean NOMIsKey() {
			return false;
		}

		public Integer NOMLength() {
			return null;
		}

		public Integer NOMPrecision() {
			return null;
		}

		public String NOMDefault() {

			return "";

		}

		public String NOMComment() {

			return "";

		}

		public String NOMPattern() {

			return "";

		}

		public String NOMOriginalDbColumnName() {

			return "NOM";

		}

		public String PRENOM;

		public String getPRENOM() {
			return this.PRENOM;
		}

		public Boolean PRENOMIsNullable() {
			return true;
		}

		public Boolean PRENOMIsKey() {
			return false;
		}

		public Integer PRENOMLength() {
			return null;
		}

		public Integer PRENOMPrecision() {
			return null;
		}

		public String PRENOMDefault() {

			return "";

		}

		public String PRENOMComment() {

			return "";

		}

		public String PRENOMPattern() {

			return "";

		}

		public String PRENOMOriginalDbColumnName() {

			return "PRENOM";

		}

		public String VILLE;

		public String getVILLE() {
			return this.VILLE;
		}

		public Boolean VILLEIsNullable() {
			return true;
		}

		public Boolean VILLEIsKey() {
			return false;
		}

		public Integer VILLELength() {
			return null;
		}

		public Integer VILLEPrecision() {
			return null;
		}

		public String VILLEDefault() {

			return "";

		}

		public String VILLEComment() {

			return "";

		}

		public String VILLEPattern() {

			return "";

		}

		public String VILLEOriginalDbColumnName() {

			return "VILLE";

		}

		public String ETAT;

		public String getETAT() {
			return this.ETAT;
		}

		public Boolean ETATIsNullable() {
			return true;
		}

		public Boolean ETATIsKey() {
			return false;
		}

		public Integer ETATLength() {
			return null;
		}

		public Integer ETATPrecision() {
			return null;
		}

		public String ETATDefault() {

			return "";

		}

		public String ETATComment() {

			return "";

		}

		public String ETATPattern() {

			return "";

		}

		public String ETATOriginalDbColumnName() {

			return "ETAT";

		}

		public Integer SALAIRE;

		public Integer getSALAIRE() {
			return this.SALAIRE;
		}

		public Boolean SALAIREIsNullable() {
			return true;
		}

		public Boolean SALAIREIsKey() {
			return false;
		}

		public Integer SALAIRELength() {
			return null;
		}

		public Integer SALAIREPrecision() {
			return null;
		}

		public String SALAIREDefault() {

			return "";

		}

		public String SALAIREComment() {

			return "";

		}

		public String SALAIREPattern() {

			return "";

		}

		public String SALAIREOriginalDbColumnName() {

			return "SALAIRE";

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_TALENDTEST_jGenerateData.length) {
					if (length < 1024 && commonByteArray_TALENDTEST_jGenerateData.length == 0) {
						commonByteArray_TALENDTEST_jGenerateData = new byte[1024];
					} else {
						commonByteArray_TALENDTEST_jGenerateData = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_TALENDTEST_jGenerateData, 0, length);
				strReturn = new String(commonByteArray_TALENDTEST_jGenerateData, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_TALENDTEST_jGenerateData) {

				try {

					int length = 0;

					this.IDENTIFIANT = readInteger(dis);

					this.NOM = readString(dis);

					this.PRENOM = readString(dis);

					this.VILLE = readString(dis);

					this.ETAT = readString(dis);

					this.SALAIRE = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.IDENTIFIANT, dos);

				// String

				writeString(this.NOM, dos);

				// String

				writeString(this.PRENOM, dos);

				// String

				writeString(this.VILLE, dos);

				// String

				writeString(this.ETAT, dos);

				// Integer

				writeInteger(this.SALAIRE, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("IDENTIFIANT=" + String.valueOf(IDENTIFIANT));
			sb.append(",NOM=" + NOM);
			sb.append(",PRENOM=" + PRENOM);
			sb.append(",VILLE=" + VILLE);
			sb.append(",ETAT=" + ETAT);
			sb.append(",SALAIRE=" + String.valueOf(SALAIRE));
			sb.append("]");

			return sb.toString();
		}

		public String toLogString() {
			StringBuilder sb = new StringBuilder();

			if (IDENTIFIANT == null) {
				sb.append("<null>");
			} else {
				sb.append(IDENTIFIANT);
			}

			sb.append("|");

			if (NOM == null) {
				sb.append("<null>");
			} else {
				sb.append(NOM);
			}

			sb.append("|");

			if (PRENOM == null) {
				sb.append("<null>");
			} else {
				sb.append(PRENOM);
			}

			sb.append("|");

			if (VILLE == null) {
				sb.append("<null>");
			} else {
				sb.append(VILLE);
			}

			sb.append("|");

			if (ETAT == null) {
				sb.append("<null>");
			} else {
				sb.append(ETAT);
			}

			sb.append("|");

			if (SALAIRE == null) {
				sb.append("<null>");
			} else {
				sb.append(SALAIRE);
			}

			sb.append("|");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tRowGenerator_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		mdcInfo.forEach(org.slf4j.MDC::put);
		org.slf4j.MDC.put("_subJobName", "tRowGenerator_1");
		org.slf4j.MDC.put("_subJobPid", "sdVbmi_" + subJobPidCounter.getAndIncrement());

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				row3Struct row3 = new row3Struct();
				row4Struct row4 = new row4Struct();
				row5Struct row5 = new row5Struct();

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row2");

				int tos_count_tLogRow_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tLogRow_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tLogRow_1 = new StringBuilder();
							log4jParamters_tLogRow_1.append("Parameters:");
							log4jParamters_tLogRow_1.append("BASIC_MODE" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("TABLE_PRINT" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("VERTICAL" + " = " + "false");
							log4jParamters_tLogRow_1.append(" | ");
							log4jParamters_tLogRow_1.append("PRINT_CONTENT_WITH_LOG4J" + " = " + "true");
							log4jParamters_tLogRow_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tLogRow_1 - " + (log4jParamters_tLogRow_1));
						}
					}
					new BytesLimit65535_tLogRow_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tLogRow_1", "tLogRow_1", "tLogRow");
					talendJobLogProcess(globalMap);
				}

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[6];

					public void addRow(String[] row) {

						for (int i = 0; i < 6; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 5 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 5 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|%6$-");
							sbformat.append(colLengths[5]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[4] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[5] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(new String[] { "IDENTIFIANT", "NOM", "PRENOM", "VILLE", "ETAT", "SALAIRE", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tFileOutputExcel_1 begin ] start
				 */

				ok_Hash.put("tFileOutputExcel_1", false);
				start_Hash.put("tFileOutputExcel_1", System.currentTimeMillis());

				currentComponent = "tFileOutputExcel_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row3");

				int tos_count_tFileOutputExcel_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputExcel_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileOutputExcel_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileOutputExcel_1 = new StringBuilder();
							log4jParamters_tFileOutputExcel_1.append("Parameters:");
							log4jParamters_tFileOutputExcel_1.append("VERSION_2007" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("USESTREAM" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1
									.append("FILENAME" + " = " + "\"C:/Users/omar-/Desktop/salaire.xls\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("SHEETNAME" + " = " + "\"Sheet1\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("INCLUDEHEADER" + " = " + "true");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("APPEND_FILE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FIRST_CELL_Y_ABSOLUTE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("FONT" + " = " + "");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("IS_ALL_AUTO_SZIE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("AUTO_SZIE_SETTING" + " = " + "[{IS_AUTO_SIZE="
									+ ("false") + ", SCHEMA_COLUMN=" + ("IDENTIFIANT") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("NOM") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("PRENOM") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("VILLE") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("ETAT") + "}, {IS_AUTO_SIZE=" + ("false")
									+ ", SCHEMA_COLUMN=" + ("SALAIRE") + "}]");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("CREATE" + " = " + "true");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							log4jParamters_tFileOutputExcel_1.append("DELETE_EMPTYFILE" + " = " + "false");
							log4jParamters_tFileOutputExcel_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileOutputExcel_1 - " + (log4jParamters_tFileOutputExcel_1));
						}
					}
					new BytesLimit65535_tFileOutputExcel_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileOutputExcel_1", "tFileOutputExcel_1", "tFileOutputExcel");
					talendJobLogProcess(globalMap);
				}

				int columnIndex_tFileOutputExcel_1 = 0;
				boolean headerIsInserted_tFileOutputExcel_1 = false;

				int nb_line_tFileOutputExcel_1 = 0;

				String fileName_tFileOutputExcel_1 = "C:/Users/omar-/Desktop/salaire.xls";
				java.io.File file_tFileOutputExcel_1 = new java.io.File(fileName_tFileOutputExcel_1);
				boolean isFileGenerated_tFileOutputExcel_1 = true;
//create directory only if not exists
				java.io.File parentFile_tFileOutputExcel_1 = file_tFileOutputExcel_1.getParentFile();
				if (parentFile_tFileOutputExcel_1 != null && !parentFile_tFileOutputExcel_1.exists()) {

					log.info("tFileOutputExcel_1 - Creating directory '"
							+ parentFile_tFileOutputExcel_1.getCanonicalPath() + "'.");

					parentFile_tFileOutputExcel_1.mkdirs();

					log.info("tFileOutputExcel_1 - Create directory '"
							+ parentFile_tFileOutputExcel_1.getCanonicalPath() + "' has succeeded.");

				}

				jxl.write.WritableWorkbook writeableWorkbook_tFileOutputExcel_1 = null;
				jxl.write.WritableSheet writableSheet_tFileOutputExcel_1 = null;

				jxl.WorkbookSettings workbookSettings_tFileOutputExcel_1 = new jxl.WorkbookSettings();
				workbookSettings_tFileOutputExcel_1.setEncoding("ISO-8859-15");
				writeableWorkbook_tFileOutputExcel_1 = new jxl.write.biff.WritableWorkbookImpl(
						new java.io.BufferedOutputStream(new java.io.FileOutputStream(fileName_tFileOutputExcel_1)),
						true, workbookSettings_tFileOutputExcel_1);

				writableSheet_tFileOutputExcel_1 = writeableWorkbook_tFileOutputExcel_1.getSheet("Sheet1");
				if (writableSheet_tFileOutputExcel_1 == null) {
					writableSheet_tFileOutputExcel_1 = writeableWorkbook_tFileOutputExcel_1.createSheet("Sheet1",
							writeableWorkbook_tFileOutputExcel_1.getNumberOfSheets());
				}

				// modif start
				int startRowNum_tFileOutputExcel_1 = writableSheet_tFileOutputExcel_1.getRows();
				// modif end

				int[] fitWidth_tFileOutputExcel_1 = new int[6];
				for (int i_tFileOutputExcel_1 = 0; i_tFileOutputExcel_1 < 6; i_tFileOutputExcel_1++) {
					int fitCellViewSize_tFileOutputExcel_1 = writableSheet_tFileOutputExcel_1
							.getColumnView(i_tFileOutputExcel_1).getSize();
					fitWidth_tFileOutputExcel_1[i_tFileOutputExcel_1] = fitCellViewSize_tFileOutputExcel_1 / 256;
					if (fitCellViewSize_tFileOutputExcel_1 % 256 != 0) {
						fitWidth_tFileOutputExcel_1[i_tFileOutputExcel_1] += 1;
					}
				}

				if (startRowNum_tFileOutputExcel_1 == 0) {
					// modif end
					// modif start
					writableSheet_tFileOutputExcel_1
							.addCell(new jxl.write.Label(0, nb_line_tFileOutputExcel_1, "IDENTIFIANT"));
					// modif end
					fitWidth_tFileOutputExcel_1[0] = fitWidth_tFileOutputExcel_1[0] > 11
							? fitWidth_tFileOutputExcel_1[0]
							: 11;
					// modif start
					writableSheet_tFileOutputExcel_1.addCell(new jxl.write.Label(1, nb_line_tFileOutputExcel_1, "NOM"));
					// modif end
					fitWidth_tFileOutputExcel_1[1] = fitWidth_tFileOutputExcel_1[1] > 3 ? fitWidth_tFileOutputExcel_1[1]
							: 3;
					// modif start
					writableSheet_tFileOutputExcel_1
							.addCell(new jxl.write.Label(2, nb_line_tFileOutputExcel_1, "PRENOM"));
					// modif end
					fitWidth_tFileOutputExcel_1[2] = fitWidth_tFileOutputExcel_1[2] > 6 ? fitWidth_tFileOutputExcel_1[2]
							: 6;
					// modif start
					writableSheet_tFileOutputExcel_1
							.addCell(new jxl.write.Label(3, nb_line_tFileOutputExcel_1, "VILLE"));
					// modif end
					fitWidth_tFileOutputExcel_1[3] = fitWidth_tFileOutputExcel_1[3] > 5 ? fitWidth_tFileOutputExcel_1[3]
							: 5;
					// modif start
					writableSheet_tFileOutputExcel_1
							.addCell(new jxl.write.Label(4, nb_line_tFileOutputExcel_1, "ETAT"));
					// modif end
					fitWidth_tFileOutputExcel_1[4] = fitWidth_tFileOutputExcel_1[4] > 4 ? fitWidth_tFileOutputExcel_1[4]
							: 4;
					// modif start
					writableSheet_tFileOutputExcel_1
							.addCell(new jxl.write.Label(5, nb_line_tFileOutputExcel_1, "SALAIRE"));
					// modif end
					fitWidth_tFileOutputExcel_1[5] = fitWidth_tFileOutputExcel_1[5] > 7 ? fitWidth_tFileOutputExcel_1[5]
							: 7;
					nb_line_tFileOutputExcel_1++;
					headerIsInserted_tFileOutputExcel_1 = true;
				}

				/**
				 * [tFileOutputExcel_1 begin ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_1", false);
				start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row4");

				int tos_count_tFileOutputDelimited_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileOutputDelimited_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileOutputDelimited_1 = new StringBuilder();
							log4jParamters_tFileOutputDelimited_1.append("Parameters:");
							log4jParamters_tFileOutputDelimited_1.append("USESTREAM" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1
									.append("FILENAME" + " = " + "\"C:/Users/omar-/Desktop/salire.csv\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FIELDSEPARATOR" + " = " + "\";\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("APPEND" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("INCLUDEHEADER" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("COMPRESS" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("CSV_OPTION" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("CREATE" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("SPLIT" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FLUSHONROW" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ROW_MODE" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("DELETE_EMPTYFILE" + " = " + "false");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							log4jParamters_tFileOutputDelimited_1.append("FILE_EXIST_EXCEPTION" + " = " + "true");
							log4jParamters_tFileOutputDelimited_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileOutputDelimited_1 - " + (log4jParamters_tFileOutputDelimited_1));
						}
					}
					new BytesLimit65535_tFileOutputDelimited_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileOutputDelimited_1", "tFileOutputDelimited_1", "tFileOutputDelimited");
					talendJobLogProcess(globalMap);
				}

				String fileName_tFileOutputDelimited_1 = "";
				fileName_tFileOutputDelimited_1 = (new java.io.File("C:/Users/omar-/Desktop/salire.csv"))
						.getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_1 = null;
				String extension_tFileOutputDelimited_1 = null;
				String directory_tFileOutputDelimited_1 = null;
				if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
							fileName_tFileOutputDelimited_1.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					}
					directory_tFileOutputDelimited_1 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_1 = true;
				java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
				if (filetFileOutputDelimited_1.exists()) {
					throw new RuntimeException("The particular file \"" + filetFileOutputDelimited_1.getAbsoluteFile()
							+ "\" already exist. If you want to overwrite the file, please uncheck the"
							+ " \"Throw an error if the file already exist\" option in Advanced settings.");
				}
				int nb_line_tFileOutputDelimited_1 = 0;
				int splitedFileNo_tFileOutputDelimited_1 = 0;
				int currentRow_tFileOutputDelimited_1 = 0;

				final String OUT_DELIM_tFileOutputDelimited_1 = /** Start field tFileOutputDelimited_1:FIELDSEPARATOR */
						";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
																		 * Start field
																		 * tFileOutputDelimited_1:ROWSEPARATOR
																		 */
						"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
					if (!dir_tFileOutputDelimited_1.exists()) {
						log.info("tFileOutputDelimited_1 - Creating directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath() + "'.");
						dir_tFileOutputDelimited_1.mkdirs();
						log.info("tFileOutputDelimited_1 - The directory '"
								+ dir_tFileOutputDelimited_1.getCanonicalPath() + "' has been created successfully.");
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_1 = null;

				java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				if (fileToDelete_tFileOutputDelimited_1.exists()) {
					fileToDelete_tFileOutputDelimited_1.delete();
				}
				outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "ISO-8859-15"));
				resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
				if (filetFileOutputDelimited_1.length() == 0) {
					outtFileOutputDelimited_1.write("IDENTIFIANT");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("NOM");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("PRENOM");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("VILLE");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("ETAT");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("SALAIRE");
					outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.flush();
				}

				resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

				/**
				 * [tFileOutputDelimited_1 begin ] stop
				 */

				/**
				 * [tFileOutputJSON_1 begin ] start
				 */

				ok_Hash.put("tFileOutputJSON_1", false);
				start_Hash.put("tFileOutputJSON_1", System.currentTimeMillis());

				currentComponent = "tFileOutputJSON_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row5");

				int tos_count_tFileOutputJSON_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tFileOutputJSON_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tFileOutputJSON_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tFileOutputJSON_1 = new StringBuilder();
							log4jParamters_tFileOutputJSON_1.append("Parameters:");
							log4jParamters_tFileOutputJSON_1
									.append("FILENAME" + " = " + "\"C:/Users/omar-/Desktop/salaire.json\"");
							log4jParamters_tFileOutputJSON_1.append(" | ");
							log4jParamters_tFileOutputJSON_1.append("GENERATE_JSON_ARRAY" + " = " + "false");
							log4jParamters_tFileOutputJSON_1.append(" | ");
							log4jParamters_tFileOutputJSON_1.append("DATABLOCKNAME" + " = " + "\"data\"");
							log4jParamters_tFileOutputJSON_1.append(" | ");
							log4jParamters_tFileOutputJSON_1.append("CREATE" + " = " + "true");
							log4jParamters_tFileOutputJSON_1.append(" | ");
							if (log.isDebugEnabled())
								log.debug("tFileOutputJSON_1 - " + (log4jParamters_tFileOutputJSON_1));
						}
					}
					new BytesLimit65535_tFileOutputJSON_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tFileOutputJSON_1", "tFileOutputJSON_1", "tFileOutputJSON");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tFileOutputJSON_1 = 0;
				java.io.File file_tFileOutputJSON_1 = new java.io.File("C:/Users/omar-/Desktop/salaire.json");
				java.io.File dir_tFileOutputJSON_1 = file_tFileOutputJSON_1.getParentFile();
				if (dir_tFileOutputJSON_1 != null && !dir_tFileOutputJSON_1.exists()) {
					dir_tFileOutputJSON_1.mkdirs();
				}
				java.io.PrintWriter outtFileOutputJSON_1 = new java.io.PrintWriter(
						new java.io.BufferedWriter(new java.io.FileWriter("C:/Users/omar-/Desktop/salaire.json")));
				outtFileOutputJSON_1.append("{\"" + "data" + "\":[");
				boolean isFirst_tFileOutputJSON_1 = true;

				/**
				 * [tFileOutputJSON_1 begin ] stop
				 */

				/**
				 * [tReplicate_1 begin ] start
				 */

				ok_Hash.put("tReplicate_1", false);
				start_Hash.put("tReplicate_1", System.currentTimeMillis());

				currentComponent = "tReplicate_1";

				runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");

				int tos_count_tReplicate_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tReplicate_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tReplicate_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tReplicate_1 = new StringBuilder();
							log4jParamters_tReplicate_1.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("tReplicate_1 - " + (log4jParamters_tReplicate_1));
						}
					}
					new BytesLimit65535_tReplicate_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tReplicate_1", "tReplicate_1", "tReplicate");
					talendJobLogProcess(globalMap);
				}

				/**
				 * [tReplicate_1 begin ] stop
				 */

				/**
				 * [tRowGenerator_1 begin ] start
				 */

				ok_Hash.put("tRowGenerator_1", false);
				start_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				currentComponent = "tRowGenerator_1";

				int tos_count_tRowGenerator_1 = 0;

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Start to work."));
				if (log.isDebugEnabled()) {
					class BytesLimit65535_tRowGenerator_1 {
						public void limitLog4jByte() throws Exception {
							StringBuilder log4jParamters_tRowGenerator_1 = new StringBuilder();
							log4jParamters_tRowGenerator_1.append("Parameters:");
							if (log.isDebugEnabled())
								log.debug("tRowGenerator_1 - " + (log4jParamters_tRowGenerator_1));
						}
					}
					new BytesLimit65535_tRowGenerator_1().limitLog4jByte();
				}
				if (enableLogStash) {
					talendJobLog.addCM("tRowGenerator_1", "tRowGenerator_1", "tRowGenerator");
					talendJobLogProcess(globalMap);
				}

				int nb_line_tRowGenerator_1 = 0;
				int nb_max_row_tRowGenerator_1 = 100;

				class tRowGenerator_1Randomizer {
					public Integer getRandomIDENTIFIANT() {

						return Numeric.sequence("s1", 1, 1);

					}

					public String getRandomNOM() {

						return StringHandling.UPCASE(TalendDataGenerator.getLastName());

					}

					public String getRandomPRENOM() {

						return TalendDataGenerator.getFirstName();

					}

					public String getRandomVILLE() {

						return TalendDataGenerator.getUsCity();

					}

					public String getRandomETAT() {

						return TalendDataGenerator.getUsState();

					}

					public Integer getRandomSALAIRE() {

						return Numeric.random(2000, 15000);

					}
				}
				tRowGenerator_1Randomizer randtRowGenerator_1 = new tRowGenerator_1Randomizer();

				log.info("tRowGenerator_1 - Generating records.");
				for (int itRowGenerator_1 = 0; itRowGenerator_1 < nb_max_row_tRowGenerator_1; itRowGenerator_1++) {
					row1.IDENTIFIANT = randtRowGenerator_1.getRandomIDENTIFIANT();
					row1.NOM = randtRowGenerator_1.getRandomNOM();
					row1.PRENOM = randtRowGenerator_1.getRandomPRENOM();
					row1.VILLE = randtRowGenerator_1.getRandomVILLE();
					row1.ETAT = randtRowGenerator_1.getRandomETAT();
					row1.SALAIRE = randtRowGenerator_1.getRandomSALAIRE();
					nb_line_tRowGenerator_1++;

					log.debug("tRowGenerator_1 - Retrieving the record " + nb_line_tRowGenerator_1 + ".");

					/**
					 * [tRowGenerator_1 begin ] stop
					 */

					/**
					 * [tRowGenerator_1 main ] start
					 */

					currentComponent = "tRowGenerator_1";

					tos_count_tRowGenerator_1++;

					/**
					 * [tRowGenerator_1 main ] stop
					 */

					/**
					 * [tRowGenerator_1 process_data_begin ] start
					 */

					currentComponent = "tRowGenerator_1";

					/**
					 * [tRowGenerator_1 process_data_begin ] stop
					 */

					/**
					 * [tReplicate_1 main ] start
					 */

					currentComponent = "tReplicate_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row1", "tRowGenerator_1", "tRowGenerator_1", "tRowGenerator", "tReplicate_1",
							"tReplicate_1", "tReplicate"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
					}

					row2 = new row2Struct();

					row2.IDENTIFIANT = row1.IDENTIFIANT;
					row2.NOM = row1.NOM;
					row2.PRENOM = row1.PRENOM;
					row2.VILLE = row1.VILLE;
					row2.ETAT = row1.ETAT;
					row2.SALAIRE = row1.SALAIRE;
					row3 = new row3Struct();

					row3.IDENTIFIANT = row1.IDENTIFIANT;
					row3.NOM = row1.NOM;
					row3.PRENOM = row1.PRENOM;
					row3.VILLE = row1.VILLE;
					row3.ETAT = row1.ETAT;
					row3.SALAIRE = row1.SALAIRE;
					row4 = new row4Struct();

					row4.IDENTIFIANT = row1.IDENTIFIANT;
					row4.NOM = row1.NOM;
					row4.PRENOM = row1.PRENOM;
					row4.VILLE = row1.VILLE;
					row4.ETAT = row1.ETAT;
					row4.SALAIRE = row1.SALAIRE;
					row5 = new row5Struct();

					row5.IDENTIFIANT = row1.IDENTIFIANT;
					row5.NOM = row1.NOM;
					row5.PRENOM = row1.PRENOM;
					row5.VILLE = row1.VILLE;
					row5.ETAT = row1.ETAT;
					row5.SALAIRE = row1.SALAIRE;

					tos_count_tReplicate_1++;

					/**
					 * [tReplicate_1 main ] stop
					 */

					/**
					 * [tReplicate_1 process_data_begin ] start
					 */

					currentComponent = "tReplicate_1";

					/**
					 * [tReplicate_1 process_data_begin ] stop
					 */

					/**
					 * [tLogRow_1 main ] start
					 */

					currentComponent = "tLogRow_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row2", "tReplicate_1", "tReplicate_1", "tReplicate", "tLogRow_1", "tLogRow_1", "tLogRow"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row2 - " + (row2 == null ? "" : row2.toLogString()));
					}

///////////////////////		

					String[] row_tLogRow_1 = new String[6];

					if (row2.IDENTIFIANT != null) { //
						row_tLogRow_1[0] = String.valueOf(row2.IDENTIFIANT);

					} //

					if (row2.NOM != null) { //
						row_tLogRow_1[1] = String.valueOf(row2.NOM);

					} //

					if (row2.PRENOM != null) { //
						row_tLogRow_1[2] = String.valueOf(row2.PRENOM);

					} //

					if (row2.VILLE != null) { //
						row_tLogRow_1[3] = String.valueOf(row2.VILLE);

					} //

					if (row2.ETAT != null) { //
						row_tLogRow_1[4] = String.valueOf(row2.ETAT);

					} //

					if (row2.SALAIRE != null) { //
						row_tLogRow_1[5] = String.valueOf(row2.SALAIRE);

					} //

					util_tLogRow_1.addRow(row_tLogRow_1);
					nb_line_tLogRow_1++;
					log.info("tLogRow_1 - Content of row " + nb_line_tLogRow_1 + ": "
							+ TalendString.unionString("|", row_tLogRow_1));
//////

//////                    

///////////////////////    			

					tos_count_tLogRow_1++;

					/**
					 * [tLogRow_1 main ] stop
					 */

					/**
					 * [tLogRow_1 process_data_begin ] start
					 */

					currentComponent = "tLogRow_1";

					/**
					 * [tLogRow_1 process_data_begin ] stop
					 */

					/**
					 * [tLogRow_1 process_data_end ] start
					 */

					currentComponent = "tLogRow_1";

					/**
					 * [tLogRow_1 process_data_end ] stop
					 */

					/**
					 * [tFileOutputExcel_1 main ] start
					 */

					currentComponent = "tFileOutputExcel_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row3", "tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputExcel_1",
							"tFileOutputExcel_1", "tFileOutputExcel"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row3 - " + (row3 == null ? "" : row3.toLogString()));
					}

					if (row3.IDENTIFIANT != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 0;

						jxl.write.WritableCell cell_0_tFileOutputExcel_1 = new jxl.write.Number(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.IDENTIFIANT);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_0_tFileOutputExcel_1);
						int currentWith_0_tFileOutputExcel_1 = String
								.valueOf(((jxl.write.Number) cell_0_tFileOutputExcel_1).getValue()).trim().length();
						currentWith_0_tFileOutputExcel_1 = currentWith_0_tFileOutputExcel_1 > 10 ? 10
								: currentWith_0_tFileOutputExcel_1;
						fitWidth_tFileOutputExcel_1[0] = fitWidth_tFileOutputExcel_1[0] > currentWith_0_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[0]
								: currentWith_0_tFileOutputExcel_1 + 2;
					}

					if (row3.NOM != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 1;

						jxl.write.WritableCell cell_1_tFileOutputExcel_1 = new jxl.write.Label(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.NOM);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_1_tFileOutputExcel_1);
						int currentWith_1_tFileOutputExcel_1 = cell_1_tFileOutputExcel_1.getContents().trim().length();
						fitWidth_tFileOutputExcel_1[1] = fitWidth_tFileOutputExcel_1[1] > currentWith_1_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[1]
								: currentWith_1_tFileOutputExcel_1 + 2;
					}

					if (row3.PRENOM != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 2;

						jxl.write.WritableCell cell_2_tFileOutputExcel_1 = new jxl.write.Label(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.PRENOM);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_2_tFileOutputExcel_1);
						int currentWith_2_tFileOutputExcel_1 = cell_2_tFileOutputExcel_1.getContents().trim().length();
						fitWidth_tFileOutputExcel_1[2] = fitWidth_tFileOutputExcel_1[2] > currentWith_2_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[2]
								: currentWith_2_tFileOutputExcel_1 + 2;
					}

					if (row3.VILLE != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 3;

						jxl.write.WritableCell cell_3_tFileOutputExcel_1 = new jxl.write.Label(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.VILLE);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_3_tFileOutputExcel_1);
						int currentWith_3_tFileOutputExcel_1 = cell_3_tFileOutputExcel_1.getContents().trim().length();
						fitWidth_tFileOutputExcel_1[3] = fitWidth_tFileOutputExcel_1[3] > currentWith_3_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[3]
								: currentWith_3_tFileOutputExcel_1 + 2;
					}

					if (row3.ETAT != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 4;

						jxl.write.WritableCell cell_4_tFileOutputExcel_1 = new jxl.write.Label(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.ETAT);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_4_tFileOutputExcel_1);
						int currentWith_4_tFileOutputExcel_1 = cell_4_tFileOutputExcel_1.getContents().trim().length();
						fitWidth_tFileOutputExcel_1[4] = fitWidth_tFileOutputExcel_1[4] > currentWith_4_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[4]
								: currentWith_4_tFileOutputExcel_1 + 2;
					}

					if (row3.SALAIRE != null) {

//modif start

						columnIndex_tFileOutputExcel_1 = 5;

						jxl.write.WritableCell cell_5_tFileOutputExcel_1 = new jxl.write.Number(
								columnIndex_tFileOutputExcel_1,
								startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,

//modif end
								row3.SALAIRE);
//modif start					
						// If we keep the cell format from the existing cell in sheet

//modif ends							
						writableSheet_tFileOutputExcel_1.addCell(cell_5_tFileOutputExcel_1);
						int currentWith_5_tFileOutputExcel_1 = String
								.valueOf(((jxl.write.Number) cell_5_tFileOutputExcel_1).getValue()).trim().length();
						currentWith_5_tFileOutputExcel_1 = currentWith_5_tFileOutputExcel_1 > 10 ? 10
								: currentWith_5_tFileOutputExcel_1;
						fitWidth_tFileOutputExcel_1[5] = fitWidth_tFileOutputExcel_1[5] > currentWith_5_tFileOutputExcel_1
								? fitWidth_tFileOutputExcel_1[5]
								: currentWith_5_tFileOutputExcel_1 + 2;
					}

					nb_line_tFileOutputExcel_1++;

					log.debug(
							"tFileOutputExcel_1 - Writing the record " + nb_line_tFileOutputExcel_1 + " to the file.");

					tos_count_tFileOutputExcel_1++;

					/**
					 * [tFileOutputExcel_1 main ] stop
					 */

					/**
					 * [tFileOutputExcel_1 process_data_begin ] start
					 */

					currentComponent = "tFileOutputExcel_1";

					/**
					 * [tFileOutputExcel_1 process_data_begin ] stop
					 */

					/**
					 * [tFileOutputExcel_1 process_data_end ] start
					 */

					currentComponent = "tFileOutputExcel_1";

					/**
					 * [tFileOutputExcel_1 process_data_end ] stop
					 */

					/**
					 * [tFileOutputDelimited_1 main ] start
					 */

					currentComponent = "tFileOutputDelimited_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row4", "tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputDelimited_1",
							"tFileOutputDelimited_1", "tFileOutputDelimited"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row4 - " + (row4 == null ? "" : row4.toLogString()));
					}

					StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
					if (row4.IDENTIFIANT != null) {
						sb_tFileOutputDelimited_1.append(row4.IDENTIFIANT);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
					if (row4.NOM != null) {
						sb_tFileOutputDelimited_1.append(row4.NOM);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
					if (row4.PRENOM != null) {
						sb_tFileOutputDelimited_1.append(row4.PRENOM);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
					if (row4.VILLE != null) {
						sb_tFileOutputDelimited_1.append(row4.VILLE);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
					if (row4.ETAT != null) {
						sb_tFileOutputDelimited_1.append(row4.ETAT);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
					if (row4.SALAIRE != null) {
						sb_tFileOutputDelimited_1.append(row4.SALAIRE);
					}
					sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

					nb_line_tFileOutputDelimited_1++;
					resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

					outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());
					log.debug("tFileOutputDelimited_1 - Writing the record " + nb_line_tFileOutputDelimited_1 + ".");

					tos_count_tFileOutputDelimited_1++;

					/**
					 * [tFileOutputDelimited_1 main ] stop
					 */

					/**
					 * [tFileOutputDelimited_1 process_data_begin ] start
					 */

					currentComponent = "tFileOutputDelimited_1";

					/**
					 * [tFileOutputDelimited_1 process_data_begin ] stop
					 */

					/**
					 * [tFileOutputDelimited_1 process_data_end ] start
					 */

					currentComponent = "tFileOutputDelimited_1";

					/**
					 * [tFileOutputDelimited_1 process_data_end ] stop
					 */

					/**
					 * [tFileOutputJSON_1 main ] start
					 */

					currentComponent = "tFileOutputJSON_1";

					if (runStat.update(execStat, enableLogStash, iterateId, 1, 1

							, "row5", "tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputJSON_1",
							"tFileOutputJSON_1", "tFileOutputJSON"

					)) {
						talendJobLogProcess(globalMap);
					}

					if (log.isTraceEnabled()) {
						log.trace("row5 - " + (row5 == null ? "" : row5.toLogString()));
					}

					org.json.simple.JSONObject jsonRowtFileOutputJSON_1 = new org.json.simple.JSONObject();
					if (row5.IDENTIFIANT != null) {

						jsonRowtFileOutputJSON_1.put("IDENTIFIANT", row5.IDENTIFIANT);

					} else {
						jsonRowtFileOutputJSON_1.put("IDENTIFIANT", null);
					}

					if (row5.NOM != null) {

						jsonRowtFileOutputJSON_1.put("NOM", row5.NOM);

					} else {
						jsonRowtFileOutputJSON_1.put("NOM", null);
					}

					if (row5.PRENOM != null) {

						jsonRowtFileOutputJSON_1.put("PRENOM", row5.PRENOM);

					} else {
						jsonRowtFileOutputJSON_1.put("PRENOM", null);
					}

					if (row5.VILLE != null) {

						jsonRowtFileOutputJSON_1.put("VILLE", row5.VILLE);

					} else {
						jsonRowtFileOutputJSON_1.put("VILLE", null);
					}

					if (row5.ETAT != null) {

						jsonRowtFileOutputJSON_1.put("ETAT", row5.ETAT);

					} else {
						jsonRowtFileOutputJSON_1.put("ETAT", null);
					}

					if (row5.SALAIRE != null) {

						jsonRowtFileOutputJSON_1.put("SALAIRE", row5.SALAIRE);

					} else {
						jsonRowtFileOutputJSON_1.put("SALAIRE", null);
					}

					if (!isFirst_tFileOutputJSON_1) {
						outtFileOutputJSON_1.append(",");
					}
					isFirst_tFileOutputJSON_1 = false;
					outtFileOutputJSON_1.append(jsonRowtFileOutputJSON_1.toJSONString());
					nb_line_tFileOutputJSON_1++;
					log.debug("tFileOutputJSON_1 - Writing the record " + nb_line_tFileOutputJSON_1 + ".");

					tos_count_tFileOutputJSON_1++;

					/**
					 * [tFileOutputJSON_1 main ] stop
					 */

					/**
					 * [tFileOutputJSON_1 process_data_begin ] start
					 */

					currentComponent = "tFileOutputJSON_1";

					/**
					 * [tFileOutputJSON_1 process_data_begin ] stop
					 */

					/**
					 * [tFileOutputJSON_1 process_data_end ] start
					 */

					currentComponent = "tFileOutputJSON_1";

					/**
					 * [tFileOutputJSON_1 process_data_end ] stop
					 */

					/**
					 * [tReplicate_1 process_data_end ] start
					 */

					currentComponent = "tReplicate_1";

					/**
					 * [tReplicate_1 process_data_end ] stop
					 */

					/**
					 * [tRowGenerator_1 process_data_end ] start
					 */

					currentComponent = "tRowGenerator_1";

					/**
					 * [tRowGenerator_1 process_data_end ] stop
					 */

					/**
					 * [tRowGenerator_1 end ] start
					 */

					currentComponent = "tRowGenerator_1";

				}
				globalMap.put("tRowGenerator_1_NB_LINE", nb_line_tRowGenerator_1);
				log.info("tRowGenerator_1 - Generated records count:" + nb_line_tRowGenerator_1 + " .");

				if (log.isDebugEnabled())
					log.debug("tRowGenerator_1 - " + ("Done."));

				ok_Hash.put("tRowGenerator_1", true);
				end_Hash.put("tRowGenerator_1", System.currentTimeMillis());

				/**
				 * [tRowGenerator_1 end ] stop
				 */

				/**
				 * [tReplicate_1 end ] start
				 */

				currentComponent = "tReplicate_1";

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
						"tRowGenerator_1", "tRowGenerator_1", "tRowGenerator", "tReplicate_1", "tReplicate_1",
						"tReplicate", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tReplicate_1 - " + ("Done."));

				ok_Hash.put("tReplicate_1", true);
				end_Hash.put("tReplicate_1", System.currentTimeMillis());

				/**
				 * [tReplicate_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);
				if (log.isInfoEnabled())
					log.info("tLogRow_1 - " + ("Printed row count: ") + (nb_line_tLogRow_1) + ("."));

///////////////////////    			

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row2", 2, 0,
						"tReplicate_1", "tReplicate_1", "tReplicate", "tLogRow_1", "tLogRow_1", "tLogRow", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tLogRow_1 - " + ("Done."));

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

				/**
				 * [tFileOutputExcel_1 end ] start
				 */

				currentComponent = "tFileOutputExcel_1";

				writeableWorkbook_tFileOutputExcel_1.write();
				writeableWorkbook_tFileOutputExcel_1.close();
				if (headerIsInserted_tFileOutputExcel_1 && nb_line_tFileOutputExcel_1 > 0) {
					nb_line_tFileOutputExcel_1 = nb_line_tFileOutputExcel_1 - 1;
				}
				globalMap.put("tFileOutputExcel_1_NB_LINE", nb_line_tFileOutputExcel_1);

				log.debug("tFileOutputExcel_1 - Written records count: " + nb_line_tFileOutputExcel_1 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row3", 2, 0,
						"tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputExcel_1", "tFileOutputExcel_1",
						"tFileOutputExcel", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputExcel_1 - " + ("Done."));

				ok_Hash.put("tFileOutputExcel_1", true);
				end_Hash.put("tFileOutputExcel_1", System.currentTimeMillis());

				/**
				 * [tFileOutputExcel_1 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (outtFileOutputDelimited_1 != null) {
					outtFileOutputDelimited_1.flush();
					outtFileOutputDelimited_1.close();
				}

				globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);

				resourceMap.put("finish_tFileOutputDelimited_1", true);

				log.debug("tFileOutputDelimited_1 - Written records count: " + nb_line_tFileOutputDelimited_1 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row4", 2, 0,
						"tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputDelimited_1",
						"tFileOutputDelimited_1", "tFileOutputDelimited", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputDelimited_1 - " + ("Done."));

				ok_Hash.put("tFileOutputDelimited_1", true);
				end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_1 end ] stop
				 */

				/**
				 * [tFileOutputJSON_1 end ] start
				 */

				currentComponent = "tFileOutputJSON_1";

				outtFileOutputJSON_1.print("]}");
				outtFileOutputJSON_1.close();
				globalMap.put("tFileOutputJSON_1_NB_LINE", nb_line_tFileOutputJSON_1);

				log.debug("tFileOutputJSON_1 - Written records count: " + nb_line_tFileOutputJSON_1 + " .");

				if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row5", 2, 0,
						"tReplicate_1", "tReplicate_1", "tReplicate", "tFileOutputJSON_1", "tFileOutputJSON_1",
						"tFileOutputJSON", "output")) {
					talendJobLogProcess(globalMap);
				}

				if (log.isDebugEnabled())
					log.debug("tFileOutputJSON_1 - " + ("Done."));

				ok_Hash.put("tFileOutputJSON_1", true);
				end_Hash.put("tFileOutputJSON_1", System.currentTimeMillis());

				/**
				 * [tFileOutputJSON_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRowGenerator_1 finally ] start
				 */

				currentComponent = "tRowGenerator_1";

				/**
				 * [tRowGenerator_1 finally ] stop
				 */

				/**
				 * [tReplicate_1 finally ] start
				 */

				currentComponent = "tReplicate_1";

				/**
				 * [tReplicate_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

				/**
				 * [tFileOutputExcel_1 finally ] start
				 */

				currentComponent = "tFileOutputExcel_1";

				/**
				 * [tFileOutputExcel_1 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {

					java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_1");
					if (outtFileOutputDelimited_1 != null) {
						outtFileOutputDelimited_1.flush();
						outtFileOutputDelimited_1.close();
					}

				}

				/**
				 * [tFileOutputDelimited_1 finally ] stop
				 */

				/**
				 * [tFileOutputJSON_1 finally ] start
				 */

				currentComponent = "tFileOutputJSON_1";

				/**
				 * [tFileOutputJSON_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", 1);
	}

	public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		String cLabel = null;
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [talendJobLog begin ] start
				 */

				ok_Hash.put("talendJobLog", false);
				start_Hash.put("talendJobLog", System.currentTimeMillis());

				currentComponent = "talendJobLog";

				int tos_count_talendJobLog = 0;

				for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
					org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
							.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
							.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
							.custom("father_pid", fatherPid).custom("root_pid", rootPid);
					org.talend.logging.audit.Context log_context_talendJobLog = null;

					if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
								.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
								.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
								.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
								.rows(jcm.row_count).duration(duration).build();
						auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
						auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
								.status(jcm.status).build();
						auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
						log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
								.connectorType(jcm.component_name).connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label).build();
						auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {// log current component
																							// input line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {// log current component
																								// output/reject line
						long timeMS = jcm.end_time - jcm.start_time;
						String duration = String.valueOf(timeMS);

						log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
								.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
								.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
								.rows(jcm.total_row_number).duration(duration).build();
						auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
					} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBERROR) {
						java.lang.Exception e_talendJobLog = jcm.exception;
						if (e_talendJobLog != null) {
							try (java.io.StringWriter sw_talendJobLog = new java.io.StringWriter();
									java.io.PrintWriter pw_talendJobLog = new java.io.PrintWriter(sw_talendJobLog)) {
								e_talendJobLog.printStackTrace(pw_talendJobLog);
								builder_talendJobLog.custom("stacktrace", sw_talendJobLog.getBuffer().substring(0,
										java.lang.Math.min(sw_talendJobLog.getBuffer().length(), 512)));
							}
						}

						if (jcm.extra_info != null) {
							builder_talendJobLog.connectorId(jcm.component_id).custom("extra_info", jcm.extra_info);
						}

						log_context_talendJobLog = builder_talendJobLog
								.connectorType(jcm.component_id.substring(0, jcm.component_id.lastIndexOf('_')))
								.connectorId(jcm.component_id)
								.connectorLabel(jcm.component_label == null ? jcm.component_id : jcm.component_label)
								.build();

						auditLogger_talendJobLog.exception(log_context_talendJobLog);
					}

				}

				/**
				 * [talendJobLog begin ] stop
				 */

				/**
				 * [talendJobLog main ] start
				 */

				currentComponent = "talendJobLog";

				tos_count_talendJobLog++;

				/**
				 * [talendJobLog main ] stop
				 */

				/**
				 * [talendJobLog process_data_begin ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_begin ] stop
				 */

				/**
				 * [talendJobLog process_data_end ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog process_data_end ] stop
				 */

				/**
				 * [talendJobLog end ] start
				 */

				currentComponent = "talendJobLog";

				ok_Hash.put("talendJobLog", true);
				end_Hash.put("talendJobLog", System.currentTimeMillis());

				/**
				 * [talendJobLog end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			if (!(e instanceof TalendException)) {
				log.fatal(currentComponent + " " + e.getMessage(), e);
			}

			TalendException te = new TalendException(e, currentComponent, cLabel, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [talendJobLog finally ] start
				 */

				currentComponent = "talendJobLog";

				/**
				 * [talendJobLog finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	private final static java.util.Properties jobInfo = new java.util.Properties();
	private final static java.util.Map<String, String> mdcInfo = new java.util.HashMap<>();
	private final static java.util.concurrent.atomic.AtomicLong subJobPidCounter = new java.util.concurrent.atomic.AtomicLong();

	public static void main(String[] args) {
		final jGenerateData jGenerateDataClass = new jGenerateData();

		int exitCode = jGenerateDataClass.runJobInTOS(args);
		if (exitCode == 0) {
			log.info("TalendJob: 'jGenerateData' - Done.");
		}

		System.exit(exitCode);
	}

	private void getjobInfo() {
		final String TEMPLATE_PATH = "src/main/templates/jobInfo_template.properties";
		final String BUILD_PATH = "../jobInfo.properties";
		final String path = this.getClass().getResource("").getPath();
		if (path.lastIndexOf("target") > 0) {
			final java.io.File templateFile = new java.io.File(
					path.substring(0, path.lastIndexOf("target")).concat(TEMPLATE_PATH));
			if (templateFile.exists()) {
				readJobInfo(templateFile);
				return;
			}
		}
		readJobInfo(new java.io.File(BUILD_PATH));
	}

	private void readJobInfo(java.io.File jobInfoFile) {

		if (jobInfoFile.exists()) {
			try (java.io.InputStream is = new java.io.FileInputStream(jobInfoFile)) {
				jobInfo.load(is);
			} catch (IOException e) {

				log.debug("Read jobInfo.properties file fail: " + e.getMessage());

			}
		}
		log.info(String.format("Project name: %s\tJob name: %s\tGIT Commit ID: %s\tTalend Version: %s", projectName,
				jobName, jobInfo.getProperty("gitCommitId"), "8.0.1.20240604_0759-patch"));

	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (!"".equals(log4jLevel)) {

			if ("trace".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.TRACE);
			} else if ("debug".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.DEBUG);
			} else if ("info".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.INFO);
			} else if ("warn".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.WARN);
			} else if ("error".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.ERROR);
			} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.FATAL);
			} else if ("off".equalsIgnoreCase(log4jLevel)) {
				org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
						org.apache.logging.log4j.Level.OFF);
			}
			org.apache.logging.log4j.core.config.Configurator
					.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());

		}

		getjobInfo();
		log.info("TalendJob: 'jGenerateData' - Start.");

		java.util.Set<Object> jobInfoKeys = jobInfo.keySet();
		for (Object jobInfoKey : jobInfoKeys) {
			org.slf4j.MDC.put("_" + jobInfoKey.toString(), jobInfo.get(jobInfoKey).toString());
		}
		org.slf4j.MDC.put("_pid", pid);
		org.slf4j.MDC.put("_rootPid", rootPid);
		org.slf4j.MDC.put("_fatherPid", fatherPid);
		org.slf4j.MDC.put("_projectName", projectName);
		org.slf4j.MDC.put("_startTimestamp", java.time.ZonedDateTime.now(java.time.ZoneOffset.UTC)
				.format(java.time.format.DateTimeFormatter.ISO_INSTANT));
		org.slf4j.MDC.put("_jobRepositoryId", "_igL48DI5Ee-82ZYHS27FTw");
		org.slf4j.MDC.put("_compiledAtTimestamp", "2024-06-24T15:18:33.282534700Z");

		java.lang.management.RuntimeMXBean mx = java.lang.management.ManagementFactory.getRuntimeMXBean();
		String[] mxNameTable = mx.getName().split("@"); //$NON-NLS-1$
		if (mxNameTable.length == 2) {
			org.slf4j.MDC.put("_systemPid", mxNameTable[0]);
		} else {
			org.slf4j.MDC.put("_systemPid", String.valueOf(java.lang.Thread.currentThread().getId()));
		}

		if (enableLogStash) {
			java.util.Properties properties_talendJobLog = new java.util.Properties();
			properties_talendJobLog.setProperty("root.logger", "audit");
			properties_talendJobLog.setProperty("encoding", "UTF-8");
			properties_talendJobLog.setProperty("application.name", "Talend Studio");
			properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
			properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
			properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
			properties_talendJobLog.setProperty("log.appender", "file");
			properties_talendJobLog.setProperty("appender.file.path", "audit.json");
			properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
			properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
			properties_talendJobLog.setProperty("host", "false");

			System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
					.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
							System.getProperty(key)));

			org.apache.logging.log4j.core.config.Configurator
					.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);

			auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
					.createJobAuditLogger(properties_talendJobLog);
		}

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		org.slf4j.MDC.put("_pid", pid);

		if (rootPid == null) {
			rootPid = pid;
		}

		org.slf4j.MDC.put("_rootPid", rootPid);

		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}
		org.slf4j.MDC.put("_fatherPid", fatherPid);

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		try {
			java.util.Dictionary<String, Object> jobProperties = null;
			if (inOSGi) {
				jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

				if (jobProperties != null && jobProperties.get("context") != null) {
					contextStr = (String) jobProperties.get("context");
				}
			}

			// first load default key-value pairs from application.properties
			if (isStandaloneMS) {
				context.putAll(this.getDefaultProperties());
			}
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = jGenerateData.class.getClassLoader()
					.getResourceAsStream("talendtest/jgeneratedata_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = jGenerateData.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						if (inOSGi && jobProperties != null) {
							java.util.Enumeration<String> keys = jobProperties.keys();
							while (keys.hasMoreElements()) {
								String propKey = keys.nextElement();
								if (defaultProps.containsKey(propKey)) {
									defaultProps.put(propKey, (String) jobProperties.get(propKey));
								}
							}
						}
						context = new ContextProperties(defaultProps);
					}
					if (isStandaloneMS) {
						// override context key-value pairs if provided using --context=contextName
						defaultProps.load(inContext);
						context.putAll(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}
			// override key-value pairs if provided via --config.location=file1.file2 OR
			// --config.additional-location=file1,file2
			if (isStandaloneMS) {
				context.putAll(this.getAdditionalProperties());
			}

			// override key-value pairs if provide via command line like
			// --key1=value1,--key2=value2
			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));

		org.slf4j.MDC.put("_context", contextStr);
		log.info("TalendJob: 'jGenerateData' - Started.");
		java.util.Optional.ofNullable(org.slf4j.MDC.getCopyOfContextMap()).ifPresent(mdcInfo::putAll);

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		try {
			errorCode = null;
			Implicit_Context_DatabaseProcess(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_Implicit_Context_Database) {
			globalMap.put("Implicit_Context_Database_SUBPROCESS_STATE", -1);

			e_Implicit_Context_Database.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPreJob

		if (enableLogStash) {
			talendJobLog.addJobStartMessage();
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tRowGenerator_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tRowGenerator_1) {
			globalMap.put("tRowGenerator_1_SUBPROCESS_STATE", -1);

			e_tRowGenerator_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : jGenerateData");
		}
		if (enableLogStash) {
			talendJobLog.addJobEndMessage(startTime, end, status);
			try {
				talendJobLogProcess(globalMap);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");
		resumeUtil.flush();

		org.slf4j.MDC.remove("_subJobName");
		org.slf4j.MDC.remove("_subJobPid");
		org.slf4j.MDC.remove("_systemPid");
		log.info("TalendJob: 'jGenerateData' - Finished - status: " + status + " returnCode: " + returnCode);

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--context_file")) {
			String keyValue = arg.substring(15);
			String filePath = new String(java.util.Base64.getDecoder().decode(keyValue));
			java.nio.file.Path contextFile = java.nio.file.Paths.get(filePath);
			try (java.io.BufferedReader reader = java.nio.file.Files.newBufferedReader(contextFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					int index = -1;
					if ((index = line.indexOf('=')) > -1) {
						if (line.startsWith("--context_param")) {
							if ("id_Password".equals(context_param.getContextType(line.substring(16, index)))) {
								context_param.put(line.substring(16, index),
										routines.system.PasswordEncryptUtil.decryptPassword(line.substring(index + 1)));
							} else {
								context_param.put(line.substring(16, index), line.substring(index + 1));
							}
						} else {// --context_type
							context_param.setContextType(line.substring(15, index), line.substring(index + 1));
						}
					}
				}
			} catch (java.io.IOException e) {
				System.err.println("Could not load the context file: " + filePath);
				e.printStackTrace();
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 196213 characters generated by Talend Cloud Data Management Platform on the
 * 24 juin 2024 à 4:18:33 PM CET
 ************************************************************************************************/