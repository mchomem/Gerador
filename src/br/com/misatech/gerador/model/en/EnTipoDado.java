package br.com.misatech.gerador.model.en;

public enum EnTipoDado {
	
	// JAVA    -        BANCO MYSQL E POSTGRESQL
	// -------------------------------------------------------------
	JAVA_LONG            // BIGINT, INTEGER UNSIGNED                     
	, JAVA_BIGINTEGER    // BIGINT UNSIGNED                              
	, JAVA_BOOLEAN       // BIT                                          
	, JAVA_STRING        // CHAR, VARCHAR, TEXT, LONGVARCHAR, ENUN, SET  
	, JAVA_DATE          // DATE                                         
	, JAVA_TIME          // TIME                                         
	, JAVA_TIMESTAMP     // TIMESTAMP, DATETIME                          
	, JAVA_BIGDECIMAL    // DECIMAL, NUMERIC                             
	, JAVA_DOUBLE        // DOUBLE, FLOAT                                
	, JAVA_INT           // INT                                          
	, JAVA_FLOAT         // REAL                                         
	, JAVA_SHORT         // SMALLINT UNSIGNED                            
	, JAVA_BYTE          // TINYINT                                      
	, JAVA_ARRAY_BYTE    // BINARY, VARBINARY, LONGVARBINARY             
	, JAVA_BLOB          // BLOB                                         
	, JAVA_CLOB          // CLOB                                         

	
	// C#.NET  -        BANCO MYSQL E POSTGRESQL
	// -------------------------------------------------------------
	, CS_LONG
	, CS_ARRAY_BYTE
	, CS_BOOL
	, CS_STRING
	, CS_DATETIME
	, CS_DECIMAL
	, CS_DOUBLE
	, CS_FLOAT
	, CS_INT
	, CS_SHORT
	, CS_TIMESPAN
	, CS_SBYTE
	
	// ADICIONADOS 15/11/2015
	, CS_BITARRAY
	, CS_GUID
	, CS_IPADDRESS
	, CS_PHYSICALADDRESS
	, CS_DATETIMEOFFSET
	
	//                  ESPEC�FICOS DO POSTGRESQL
	// -------------------------------------------------------------
	, CS_NPGSQLPOINT
	, CS_NPGSQLLSEG
	, CS_NPGSQLPOLYGON
	, CS_NPGSQLLINE
	, CS_NPGSQLCIRCLE
	, CS_NPGSQLBOX
	, CS_NPGSQLINET
	, CS_NPGSQLTSQUERY
	, CS_NPGSQLTSVECTOR
	, CS_NPGSQLRANGE
	
	, DESCONHECIDO; // N�O IDENTIFICADO O TIPO NO BANCO EM QUALQUER CASO (JAVA OU C#).
	
}
