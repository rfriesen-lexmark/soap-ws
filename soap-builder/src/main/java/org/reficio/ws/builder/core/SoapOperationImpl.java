/**
 * Copyright (c) 2012-2013 Reficio (TM) - Reestablish your software!. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.reficio.ws.builder.core;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.xml.namespace.QName;

import org.reficio.ws.SoapContext;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.SoapOperationBuilder;

/**
 * @author Tom Bujok
 * @since 1.0.0
 */
class SoapOperationImpl implements SoapOperation, SoapOperationBuilder {

    private final QName bindingName;
    private final String operationName;
    private final String operationInputName;
    private final String operationOutputName;
    private final String soapAction;
    private final SoapBuilder builder;
    private SoapContext context;

    SoapOperationImpl(SoapBuilder builder, QName bindingName, String operationName, String operationInputName,
                      String operationOutputName, String soapAction) {

        this.builder = builder;
        this.bindingName = bindingName;
        this.operationName = operationName;
        this.operationInputName = operationInputName;
        this.operationOutputName = operationOutputName;
        this.soapAction = soapAction;
        this.context = builder.getContext();
    }

    public QName getBindingName() {
        return bindingName;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getOperationInputName() {
        return operationInputName;
    }

    public String getOperationOutputName() {
        return operationOutputName;
    }

    public String getSoapAction() {
        return soapAction;
    }

    @Override
    public boolean isRpc() {
        return builder.isRpc();
    }

    @Override
    public boolean isInputSoapEncoded() {
        return builder.isInputSoapEncoded(this);
    }

    @Override
    public boolean isOutputSoapEncoded() {
        return builder.isOutputSoapEncoded(this);
    }

    static SoapOperationBuilder create(SoapBuilder builder, Binding binding, BindingOperation operation) {
        String soapAction = SoapUtils.getSOAPActionUri(operation);
        return create(builder, binding, operation, soapAction);
    }

    static SoapOperationBuilder create(SoapBuilder builder, Binding binding, BindingOperation operation, String soapAction) {
        String bindingInputName = operation.getBindingInput() != null ? operation.getBindingInput().getName() : null;
        String bindingOutputName = operation.getBindingOutput() != null ? operation.getBindingOutput().getName() : null;
        return new SoapOperationImpl(builder, binding.getQName(), operation.getName(), bindingInputName, bindingOutputName,
                SoapUtils.normalizeSoapAction(soapAction));
    }

    public String toString() {
        return String.format("bindingName=[%s] operationName=[%s] operationInputName=[%s] operationOutputName=[%s] soapAction=[%s]",
                bindingName.toString(), operationName, operationInputName, operationOutputName, soapAction);
    }

    @Override
    public void setContext(SoapContext context) {
        this.context = context;
    }

    @Override
    public SoapContext getContext() {
        return builder.getContext();
    }

    @Override
    public String buildInputMessage() {
        return builder.buildInputMessage(this, context, null);
    }

    @Override
    public String buildInputMessage(SoapContext context) {
        return builder.buildInputMessage(this, context, null);
    }

    @Override
    public String buildOutputMessage() {
        return builder.buildOutputMessage(this, context);
    }

    @Override
    public String buildOutputMessage(SoapContext context) {
        return builder.buildOutputMessage(this, context);
    }

    @Override
    public String buildFault(String code, String message) {
        return builder.buildFault(code, message, context);
    }

    @Override
    public String buildFault(String code, String message, SoapContext context) {
        return builder.buildFault(code, message, context);
    }

    @Override
    public String buildEmptyFault() {
        return builder.buildEmptyFault(context);
    }

    @Override
    public String buildEmptyFault(SoapContext context) {
        return builder.buildEmptyFault(context);
    }

    @Override
    public String buildEmptyMessage() {
        return builder.buildEmptyMessage(context);
    }

    @Override
    public String buildEmptyMessage(SoapContext context) {
        return builder.buildEmptyMessage(context);
    }

    @Override
    public void validateInputMessage(String message) {
        builder.validateInputMessage(this, message);
    }

    @Override
    public void validateInputMessage(String message, boolean strict) {
        builder.validateInputMessage(this, message, strict);
    }

    @Override
    public void validateOutputMessage(String message) {
        builder.validateOutputMessage(this, message);
    }

    @Override
    public void validateOutputMessage(String message, boolean strict) {
        builder.validateOutputMessage(this, message, strict);
    }

}
