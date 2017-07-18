package info.nordbyen.survivalheaven.subplugins.bitly.util.utils;

import org.w3c.dom.*;

public final class Dom
{
    public static String getTextContent(final Node n) {
        final StringBuffer sb = new StringBuffer();
        final NodeList nl = n.getChildNodes();
        for (int i = 0; i < nl.getLength(); ++i) {
            final Node child = nl.item(i);
            if (child.getNodeType() == 3) {
                sb.append(child.getNodeValue());
            }
        }
        return sb.toString();
    }
}
