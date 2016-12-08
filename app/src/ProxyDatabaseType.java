// This class is a part of the 4th "sprint" of the database labs
// running in Saint-Petersburg Academic University in Fall'16
//
// Author: Dmitry Barashev
// License: WTFPL

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersister;
import com.j256.ormlite.field.FieldConverter;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.List;

/**
 * This class is just a dirty hack which overrides a single method #isNestedSavePointsSupported
 * and returns false. This makes transaction isolation levels working with PostgreSQL.
 */
public class ProxyDatabaseType implements DatabaseType {
  DatabaseType myDelegate;

  public ProxyDatabaseType(DatabaseType databaseType) {
    myDelegate = databaseType;
  }

  @Override
  public boolean isDatabaseUrlThisType(String url, String dbTypePart) {
    return myDelegate.isDatabaseUrlThisType(url, dbTypePart);
  }

  @Override
  public void loadDriver() throws SQLException {
    myDelegate.loadDriver();
  }

  @Override
  public void setDriver(Driver driver) {
    myDelegate.setDriver(driver);
  }

  @Override
  public void appendColumnArg(String tableName, StringBuilder sb, FieldType fieldType, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) throws SQLException {
    myDelegate.appendColumnArg(tableName, sb, fieldType, additionalArgs, statementsBefore, statementsAfter, queriesAfter);
  }

  @Override
  public void addPrimaryKeySql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) throws SQLException {
    myDelegate.addPrimaryKeySql(fieldTypes, additionalArgs, statementsBefore, statementsAfter, queriesAfter);
  }

  @Override
  public void addUniqueComboSql(FieldType[] fieldTypes, List<String> additionalArgs, List<String> statementsBefore, List<String> statementsAfter, List<String> queriesAfter) throws SQLException {
    myDelegate.addUniqueComboSql(fieldTypes, additionalArgs, statementsBefore, statementsAfter, queriesAfter);
  }

  @Override
  public void dropColumnArg(FieldType fieldType, List<String> statementsBefore, List<String> statementsAfter) {
    myDelegate.dropColumnArg(fieldType, statementsBefore, statementsAfter);
  }

  @Override
  public void appendEscapedEntityName(StringBuilder sb, String word) {
    myDelegate.appendEscapedEntityName(sb, word);
  }

  @Override
  public void appendEscapedWord(StringBuilder sb, String word) {
    myDelegate.appendEscapedWord(sb, word);
  }

  @Override
  public String generateIdSequenceName(String tableName, FieldType idFieldType) {
    return myDelegate.generateIdSequenceName(tableName, idFieldType);
  }

  @Override
  public String getCommentLinePrefix() {
    return myDelegate.getCommentLinePrefix();
  }

  @Override
  public boolean isIdSequenceNeeded() {
    return myDelegate.isIdSequenceNeeded();
  }

  @Override
  public DataPersister getDataPersister(DataPersister defaultPersister, FieldType fieldType) {
    return myDelegate.getDataPersister(defaultPersister, fieldType);
  }

  @Override
  public FieldConverter getFieldConverter(DataPersister dataType, FieldType fieldType) {
    return myDelegate.getFieldConverter(dataType, fieldType);
  }

  @Override
  public boolean isVarcharFieldWidthSupported() {
    return myDelegate.isVarcharFieldWidthSupported();
  }

  @Override
  public boolean isLimitSqlSupported() {
    return myDelegate.isLimitSqlSupported();
  }

  @Override
  public boolean isLimitAfterSelect() {
    return myDelegate.isLimitAfterSelect();
  }

  @Override
  public void appendLimitValue(StringBuilder sb, long limit, Long offset) {
    myDelegate.appendLimitValue(sb, limit, offset);
  }

  @Override
  public boolean isOffsetSqlSupported() {
    return myDelegate.isOffsetSqlSupported();
  }

  @Override
  public boolean isOffsetLimitArgument() {
    return myDelegate.isOffsetLimitArgument();
  }

  @Override
  public void appendOffsetValue(StringBuilder sb, long offset) {
    myDelegate.appendOffsetValue(sb, offset);
  }

  @Override
  public void appendSelectNextValFromSequence(StringBuilder sb, String sequenceName) {
    myDelegate.appendSelectNextValFromSequence(sb, sequenceName);
  }

  @Override
  public void appendCreateTableSuffix(StringBuilder sb) {
    myDelegate.appendCreateTableSuffix(sb);
  }

  @Override
  public boolean isCreateTableReturnsZero() {
    return myDelegate.isCreateTableReturnsZero();
  }

  @Override
  public boolean isCreateTableReturnsNegative() {
    return myDelegate.isCreateTableReturnsNegative();
  }

  @Override
  public boolean isEntityNamesMustBeUpCase() {
    return myDelegate.isEntityNamesMustBeUpCase();
  }

  @Override
  public String upCaseEntityName(String entityName) {
    return myDelegate.upCaseEntityName(entityName);
  }

  @Override
  public boolean isNestedSavePointsSupported() {
    return false;
  }

  @Override
  public String getPingStatement() {
    return myDelegate.getPingStatement();
  }

  @Override
  public boolean isBatchUseTransaction() {
    return myDelegate.isBatchUseTransaction();
  }

  @Override
  public boolean isTruncateSupported() {
    return myDelegate.isTruncateSupported();
  }

  @Override
  public boolean isCreateIfNotExistsSupported() {
    return myDelegate.isCreateIfNotExistsSupported();
  }

  @Override
  public boolean isCreateIndexIfNotExistsSupported() {
    return myDelegate.isCreateIndexIfNotExistsSupported();
  }

  @Override
  public boolean isSelectSequenceBeforeInsert() {
    return myDelegate.isSelectSequenceBeforeInsert();
  }

  @Override
  public boolean isAllowGeneratedIdInsertSupported() {
    return myDelegate.isAllowGeneratedIdInsertSupported();
  }

  @Override
  public String getDatabaseName() {
    return myDelegate.getDatabaseName();
  }

  @Override
  public <T> DatabaseTableConfig<T> extractDatabaseTableConfig(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
    return myDelegate.extractDatabaseTableConfig(connectionSource, clazz);
  }

  @Override
  public void appendInsertNoColumns(StringBuilder sb) {
    myDelegate.appendInsertNoColumns(sb);
  }
}
