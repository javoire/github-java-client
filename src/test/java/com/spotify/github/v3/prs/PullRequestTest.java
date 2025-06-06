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

package com.spotify.github.v3.prs;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.common.io.Resources;
import com.spotify.github.jackson.Json;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class PullRequestTest {

  @Test
  public void testDeserializationPr() throws IOException {
    String fixture =
        Resources.toString(getResource(this.getClass(), "pull_request.json"), defaultCharset());
    final PullRequest pr = Json.create().fromJson(fixture, PullRequest.class);

    assertThat(pr.id(), is(1L));
    assertThat(pr.nodeId(), is("MDExOlB1bGxSZXF1ZXN0NDI3NDI0Nw=="));
    assertThat(pr.mergeCommitSha().get(), is("e5bd3914e2e596debea16f433f57875b5b90bcd6"));
    assertThat(pr.merged(), is(false));
    assertThat(pr.mergeable().get(), is(true));
    assertThat(pr.comments(), is(10));
    assertThat(pr.additions(), is(100));
    assertThat(pr.deletions(), is(3));
    assertThat(pr.changedFiles(), is(5));
    assertThat(pr.draft(), is(Optional.of(false)));
    assertThat(pr.labels().size(),is(1));
    assertThat(pr.labels().get(0).name(),is("bug"));
    assertThat(pr.labels().get(0).id(),is(42L));
    assertThat(pr.labels().get(0).color(),is("ff0000"));
  }

  @Test
  public void testDeserializationPrWithLargeId() throws IOException {
    String fixture =
            Resources.toString(getResource(this.getClass(), "pull_request_long_id.json"), defaultCharset());
    final PullRequest pr = Json.create().fromJson(fixture, PullRequest.class);

    assertThat(pr.id(), is(2459198527L));
    assertThat(pr.head().sha(), is("f74c7f420282f584acd2fb5964202e5b525c3ab8"));
    assertThat(pr.merged(), is(false));
    assertThat(pr.mergeable().get(), is(false));
    assertThat(pr.draft(), is(Optional.of(true)));
  }

  @Test
  public void testSerializationMergeParams() throws IOException {
    String fixture =
        Resources.toString(getResource(this.getClass(), "merge_params_full.json"), defaultCharset());
    final MergeParameters fixtureParams = Json.create().fromJson(fixture, MergeParameters.class);

    final MergeParameters params = ImmutableMergeParameters.builder()
        .commitTitle("a title")
        .commitMessage("a message")
        .sha("6dcb09b5b57875f334f61aebed695e2e4193db5e")
        .build();
    assertThat(params.commitMessage(), is(fixtureParams.commitMessage()));
    assertThat(params.commitTitle(), is(fixtureParams.commitTitle()));
    assertThat(params.sha(), is(fixtureParams.sha()));
    assertThat(params.mergeMethod(), is(MergeMethod.merge));
  }

  @Test
  public void testSerializationAutoMergeDisabled() throws IOException {
    String fixture =
            Resources.toString(getResource(this.getClass(), "pull_request_automerge_disabled.json"), defaultCharset());
    final PullRequest pr = Json.create().fromJson(fixture, PullRequest.class);

    assertThat(pr.id(), is(2439836648L));
    assertThat(pr.head().sha(), is("881ef333d1ffc01869b666f13d3b37d7af92b9a2"));
    assertThat(pr.merged(), is(false));
    assertThat(pr.mergeable().get(), is(true));
    assertThat(pr.draft(), is(Optional.of(true)));
    assertNull(pr.autoMerge());
  }

  @Test
  public void testSerializationAutoMergeEnabled() throws IOException {
    String fixture =
            Resources.toString(getResource(this.getClass(), "pull_request_automerge_enabled.json"), defaultCharset());
    final PullRequest pr = Json.create().fromJson(fixture, PullRequest.class);

    assertThat(pr.id(), is(2439836648L));
    assertThat(pr.head().sha(), is("881ef333d1ffc01869b666f13d3b37d7af92b9a2"));
    assertThat(pr.merged(), is(false));
    assertThat(pr.mergeable().get(), is(true));
    assertThat(pr.draft(), is(Optional.of(false)));
    assertNotNull(pr.autoMerge());
    assertThat(pr.autoMerge().enabledBy().login(), is("octocat"));
    assertThat(pr.autoMerge().mergeMethod(), is("squash"));
  }

  @Test
  public void testDeserializationMergeParamsOmitsFields() throws IOException {
    final MergeParameters params = ImmutableMergeParameters.builder()
        .commitMessage("a message")
        .sha("6dcb09b5b57875f334f61aebed695e2e4193db5e")
        .build();
    final String json = Json.create().toJson(params);

    assertThat(
        json,
        is(
            "{\"sha\":\"6dcb09b5b57875f334f61aebed695e2e4193db5e\",\"commit_message\":\"a message\",\"merge_method\":\"merge\"}"));

    System.out.println(json);
  }
}
