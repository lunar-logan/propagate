<template>
  <div>
    <h1 class="title">Propagate | Feature flags</h1>

    <nav class="navbar" role="navigation" aria-label="main navigation"
         style="border-bottom: hsl(0, 0%, 86%) solid 2px;">
      <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
          <div class="navbar-item">
            <div class="control has-icons-left">
              <input class="input" type="text" placeholder="Search feature flags">
              <span class="icon is-small is-left">
                <i class="fas fa-search"></i>
              </span>
            </div>
          </div>
        </div>

        <div class="navbar-end">
          <div class="navbar-item">
            <div class="control has-icons-left">
              <div class="select">
                <select>
                  <option selected>Newest</option>
                  <option>Oldest</option>
                </select>
              </div>
              <div class="icon is-small is-left">
                <i class="fa fa-sort"></i>
              </div>
            </div>
          </div>

          <div class="navbar-item">
            <button class="button is-primary" v-on:click="showCreateNewFeatureFlagModal">
                <span class="icon is-small">
                  <i class="fas fa-plus"></i>
                </span>
              <span><strong>Feature Flag</strong></span>
            </button>
          </div>
        </div>
      </div>
    </nav>

    <table class="table is-fullwidth">
      <thead>
      <tr>
        <th>Feature Flag</th>
        <th>Maintainer</th>
        <th>Last Requested</th>
        <th>Targeting</th>
      </tr>
      </thead>
      <FeatureFlagView v-for="flag in flags" v-bind:key="flag.id" v-bind:keyCode="flag.key" v-bind:title="flag.title"/>
    </table>

    <CreateNewFeatureFlagModal v-bind:is-active="isModalActive" v-on:close="handleCancel"></CreateNewFeatureFlagModal>
  </div>
</template>

<script>
// @ is an alias to /src
import FeatureFlagView from "@/components/FeatureFlagView";
import CreateNewFeatureFlagModal from "@/components/CreateNewFeatureFlagModal";

export default {
  name: 'Home',
  components: {CreateNewFeatureFlagModal, FeatureFlagView},
  data() {
    return {
      flags: [
        {
          id: 1,
          key: "SNAPDEALTECH-90326-switch-partial-delivery",
          title: "Feature flag to control rollout of partial delivery feature"
        },
        {
          id: 2,
          key: "SNAPDEALTECH-90326-switch-partial-delivery",
          title: "Feature flag to control rollout of partial delivery feature"
        },
        {
          id: 3,
          key: "SNAPDEALTECH-90326-switch-partial-delivery",
          title: "Feature flag to control rollout of partial delivery feature"
        }
      ],
      isModalActive: false
    }
  },
  methods: {
    handleCancel() {
      this.isModalActive = false;
    },
    showCreateNewFeatureFlagModal() {
      this.isModalActive = true;
    }
  }
}
</script>
