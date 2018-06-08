//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.08 at 07:56:58 AM EEST 
//


package ua.kiev.unicyb.diploma.domain.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for questionConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="questionConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="hashtags" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="hashtag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="sources">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="source" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;all>
 *                             &lt;element name="question_source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="count_questions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *                           &lt;/all>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="estimation" type="{}estimationConfig"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "questionConfig", propOrder = {

})
public class QuestionConfig {

    protected QuestionConfig.Hashtags hashtags;
    @XmlElement(required = true)
    protected QuestionConfig.Sources sources;
    @XmlElement(required = true)
    protected EstimationConfig estimation;

    /**
     * Gets the value of the hashtags property.
     * 
     * @return
     *     possible object is
     *     {@link QuestionConfig.Hashtags }
     *     
     */
    public QuestionConfig.Hashtags getHashtags() {
        return hashtags;
    }

    /**
     * Sets the value of the hashtags property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionConfig.Hashtags }
     *     
     */
    public void setHashtags(QuestionConfig.Hashtags value) {
        this.hashtags = value;
    }

    /**
     * Gets the value of the sources property.
     * 
     * @return
     *     possible object is
     *     {@link QuestionConfig.Sources }
     *     
     */
    public QuestionConfig.Sources getSources() {
        return sources;
    }

    /**
     * Sets the value of the sources property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionConfig.Sources }
     *     
     */
    public void setSources(QuestionConfig.Sources value) {
        this.sources = value;
    }

    /**
     * Gets the value of the estimation property.
     * 
     * @return
     *     possible object is
     *     {@link EstimationConfig }
     *     
     */
    public EstimationConfig getEstimation() {
        return estimation;
    }

    /**
     * Sets the value of the estimation property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstimationConfig }
     *     
     */
    public void setEstimation(EstimationConfig value) {
        this.estimation = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="hashtag" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "hashtag"
    })
    public static class Hashtags {

        @XmlElement(required = true)
        protected List<String> hashtag;

        /**
         * Gets the value of the hashtag property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hashtag property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHashtag().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getHashtag() {
            if (hashtag == null) {
                hashtag = new ArrayList<String>();
            }
            return this.hashtag;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="source" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;all>
     *                   &lt;element name="question_source" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="count_questions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
     *                 &lt;/all>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "source"
    })
    public static class Sources {

        @XmlElement(required = true)
        protected List<QuestionConfig.Sources.Source> source;

        /**
         * Gets the value of the source property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the source property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link QuestionConfig.Sources.Source }
         * 
         * 
         */
        public List<QuestionConfig.Sources.Source> getSource() {
            if (source == null) {
                source = new ArrayList<QuestionConfig.Sources.Source>();
            }
            return this.source;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;all>
         *         &lt;element name="question_source" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="count_questions" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
         *       &lt;/all>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {

        })
        public static class Source {

            @XmlElement(name = "question_source", required = true)
            protected String questionSource;
            @XmlElement(name = "count_questions", defaultValue = "1")
            @XmlSchemaType(name = "nonNegativeInteger")
            protected BigInteger countQuestions;

            /**
             * Gets the value of the questionSource property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getQuestionSource() {
                return questionSource;
            }

            /**
             * Sets the value of the questionSource property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setQuestionSource(String value) {
                this.questionSource = value;
            }

            /**
             * Gets the value of the countQuestions property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getCountQuestions() {
                return countQuestions;
            }

            /**
             * Sets the value of the countQuestions property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setCountQuestions(BigInteger value) {
                this.countQuestions = value;
            }

        }

    }

}
