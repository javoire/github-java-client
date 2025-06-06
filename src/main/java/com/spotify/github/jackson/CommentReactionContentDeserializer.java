/*-
 * -\-\-
 * github-api
 * --
 * Copyright (C) 2016 - 2020 Spotify AB
 * --
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
 * -/-/-
 */
package com.spotify.github.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.spotify.github.v3.comment.CommentReactionContent;

import java.io.IOException;
/**
 * Custom deserializer for {@link CommentReactionContent}.
 */
public class CommentReactionContentDeserializer extends JsonDeserializer<CommentReactionContent> {
  @Override
  public CommentReactionContent deserialize(final JsonParser p, final DeserializationContext ctxt)
      throws IOException {
    String value = p.getText();
    for (CommentReactionContent content : CommentReactionContent.values()) {
      if (content.toString().equals(value)) {
        return content;
      }
    }
    return null;
  }
}
