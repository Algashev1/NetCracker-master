<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>test1</title>
                <style type="text/css">
                    h3{
                    text-align: center;
                    }
                    table{
                    margin-top: 10px;
                    margin-bottom: 30px;
                    margin-left: auto;
                    margin-right: auto;
                    table-layout: fixed;
                    border-collapse: collapse;
                    }
                </style>
            </head>
            <body>
                <div class = "test2">
                    <h3>Вывод через преобразование XSLT</h3>
                    <table>
                        <xsl:for-each select="taskinfo/task">
                            <tr>
                                <td>Name</td>
                                <td>
                                    <input type="text" name="name">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="t_name" />
                                        </xsl:attribute>
                                    </input>
                                </td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td>
                                    <input type="text" name="description">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="t_description" />
                                        </xsl:attribute>
                                    </input>
                                </td>
                            </tr>
                            <tr>
                                <td>Time</td>
                                <td>
                                    <input type="text" name="time">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="t_data" />
                                        </xsl:attribute>
                                    </input>
                                </td>
                            </tr>
                            <tr>
                                <td>Contacts</td>
                                <td>
                                    <input type="text" name="contacts">
                                        <xsl:attribute name="value">
                                            <xsl:value-of select="t_contacts" />
                                        </xsl:attribute>
                                    </input>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
