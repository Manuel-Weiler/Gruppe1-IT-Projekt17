package de.hdm.gruppe1.Project4u.shared.report;

import java.io.Serializable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Diese Klasse stellt einzelne Absaetze dar. Der Absatzinhalt wird als String
 * gespeichert. Der Anwender sollte in diesem Strinig keinerlei
 * Formatierungssymbole einfuegen, da diese in der Regel zielformatspezifisch
 * sind.
 * 
 * @author Thies
 */

public class SimpleParagraph extends Paragraph implements IsSerializable, Serializable{
	
	  private static final long serialVersionUID = 1L;

	  /**
	   * Inhalt des Absatzes.
	   */
	  private String text = "";

	  /**
	   * Serialisierbare Klassen, die mittels GWT-RPC transportiert werden sollen,
	   * muessen einen No-Argument-Konstruktor besitzen. Ist kein Konstruktor
	   * explizit angegeben, so existiert ini Java-Klassen implizit der
	   * Default-Konstruktor, der dem No-Argument-Konstruktor entspricht.
	   * 
	   * Besitzt eine Klasse mind. einen explizit implementierten Konstruktor, so
	   * gelten nur diese explizit implementierten Konstruktoren. Der
	   * Default-Konstruktor gilt dann nicht. Wenn wir in einer solchen Situation
	   * aber dennoch einen No-Argument-Konstruktor benoetigen, muessen wir diesen wie
	   * in diesem Beispiel explizit implementieren.
	   * 
	   * @see #SimpleParagraph(String)
	   */
	  public SimpleParagraph() {
	  }

	  /**
	   * Dieser Konstruktor ermoeglicht es, bereits bei Instantiierung von
	   * SimpleParagraph-Objekten deren Inhalt angeben zu koennen.
	   * 
	   * @param value der Inhalt des Absatzes
	   * @see #SimpleParagraph()
	   */
	  public SimpleParagraph(String value) {
	    this.text = value;
	  }

	  /**
	   * Auslesen des Inhalts.
	   * 
	   * @return Inhalt als String
	   */
	  public String getText() {
	    return this.text;
	  }

	  /**
	   * Ueberschreiben des Inhalts.
	   * 
	   * @param text der neue Inhalt des Absatzes.
	   */
	  public void setText(String text) {
	    this.text = text;
	  }

	  /**
	   * Umwandeln des SimpleParagraph-Objekts in einen String.
	   */
	  @Override
	  public String toString() {
	    return this.text;
	  }
	}