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

package com.spotify.github.v3.checks;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spotify.github.GithubStyle;

import java.util.List;
import java.util.Optional;

import com.spotify.github.v3.prs.PartialPullRequestItem;
import org.immutables.value.Value;

/** GitHub CheckSuite */
@Value.Immutable
@GithubStyle
@JsonDeserialize(as = ImmutableCheckSuite.class)
public interface CheckSuite {

  /**
   * The Check Suite id.
   *
   * @return the long id
   */
  Long id();

  Optional<App> app();

  Optional<String> headBranch();

  /**
   * Pull Requests where this check suite is applied.
   *
   * @return the list of pull requests
   */
  List<PartialPullRequestItem> pullRequests();
}
