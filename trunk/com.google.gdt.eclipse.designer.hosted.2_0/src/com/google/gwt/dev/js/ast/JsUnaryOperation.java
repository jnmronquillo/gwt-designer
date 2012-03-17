/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gwt.dev.js.ast;

import com.google.gwt.dev.jjs.SourceInfo;

/**
 * A JavaScript prefix or postfix operation.
 */
public abstract class JsUnaryOperation extends JsExpression {

  private JsExpression arg;

  private final JsUnaryOperator op;

  public JsUnaryOperation(SourceInfo sourceInfo, JsUnaryOperator op) {
    this(sourceInfo, op, null);
  }

  public JsUnaryOperation(SourceInfo sourceInfo, JsUnaryOperator op,
      JsExpression arg) {
    super(sourceInfo);
    this.op = op;
    this.arg = arg;
  }

  public JsExpression getArg() {
    return arg;
  }

  public JsUnaryOperator getOperator() {
    return op;
  }

  @Override
  public final boolean hasSideEffects() {
    return op.isModifying() || arg.hasSideEffects();
  }

  public void setArg(JsExpression arg) {
    this.arg = arg;
  }

  public void traverse(JsVisitor v, JsContext<JsExpression> ctx) {
    if (op.isModifying()) {
      /*
       * The delete operator is practically like an assignment of undefined, so
       * for practical purposes we're treating it as an lvalue.
       */
      arg = v.acceptLvalue(arg);
    } else {
      arg = v.accept(arg);
    }
  }

}