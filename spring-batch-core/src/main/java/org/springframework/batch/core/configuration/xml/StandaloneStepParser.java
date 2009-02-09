/*
 * Copyright 2006-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.configuration.xml;

import java.util.List;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Internal parser for the &lt;step/&gt; elements inside a job. A step element
 * references a bean definition for a {@link org.springframework.batch.core.Step} and goes on to (optionally)
 * list a set of transitions from that step to others with &lt;next on="pattern"
 * to="stepName"/&gt;. Used by the {@link JobParser}.
 * 
 * @see JobParser
 * 
 * @author Dave Syer
 * @author Thomas Risberg
 * @since 2.0
 */
public class StandaloneStepParser extends AbstractStepParser {

	/**
	 * Parse the step and turn it into a list of transitions.
	 * 
	 * @param element the &lt;step/gt; element to parse
	 * @param parserContext the parser context for the bean factory
	 */
	public AbstractBeanDefinition parse(Element element, ParserContext parserContext) {

//		String stepId = element.getAttribute("id");
		String taskletRef = element.getAttribute("tasklet");

//		TODO: this should be required in xsd
//		if (!StringUtils.hasText(stepId)) {
//			parserContext.getReaderContext().error("The id attribute can't be empty for <" + element.getNodeName() + ">", element);
//		}
		
		@SuppressWarnings("unchecked")
		List<Element> processTaskElements = (List<Element>) DomUtils.getChildElementsByTagName(element, "tasklet");
		AbstractBeanDefinition bd = null;
		if (StringUtils.hasText(taskletRef)) {
			bd = handleTaskletRef(element, taskletRef, parserContext);
		}
		else if (processTaskElements.size() > 0) {
			Element taskElement = processTaskElements.get(0);
			bd = handleTaskletElement(element, taskElement, parserContext);
		}
		
		return bd;
		
	}

}