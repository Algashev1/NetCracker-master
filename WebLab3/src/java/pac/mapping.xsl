<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Проверка вывода данных через XSLT</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Name</th>
                        <th>Index</th>
                        <th>Description</th>
                        <th>Time</th>
                        <th>Contacts</th>
                    </tr>
                    <xsl:for-each select="taskinfo/task">
                        <tr>
                            <td>
                                <xsl:value-of select="t_name"/>
                            </td>
                            <td>
                                <xsl:value-of select="t_index"/>
                            </td>
                            <td>
                                <xsl:value-of select="t_description"/>
                            </td>
                            <td>
                                <xsl:value-of select="t_data"/>
                            </td>
                            <td>
                                <xsl:value-of select="t_contacts"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <a href="tasks.jsp">Назад</a>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
