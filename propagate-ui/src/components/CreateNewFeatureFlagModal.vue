<template>
  <div class="modal" v-bind:class="{'modal': !isActive, 'modal is-active': isActive}">
    <div class="modal-background"></div>
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Create feature flag</p>
        <button class="delete" aria-label="close" v-on:click="handleClose"></button>
      </header>
      <section class="modal-card-body">
        <div class="field">
          <label class="label">Title</label>
          <div class="control">
            <input class="input" type="text" placeholder="Title of the feature flag">
          </div>
        </div>

        <div class="field">
          <label class="label">Key</label>
          <div class="control">
            <input class="input" type="text" placeholder="Unique key for this feature flag">
          </div>
        </div>

        <div class="field">
          <label class="label">Feature flag type</label>
          <div class="control">
            <div class="select" v-on:select="handleFFTypeSelect">
              <select>
                <option selected>Boolean</option>
                <option>String</option>
                <option>JSON*</option>
              </select>
            </div>
          </div>
        </div>
      </section>
      <footer class="modal-card-foot">
        <button class="button is-success">Save changes</button>
        <button class="button" >Cancel</button>
      </footer>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateNewFeatureFlagModal',
  props: ['isActive'],
  methods: {
    handleClose() {
      console.log('emitting close');
      this.$emit('close');
    },
    handleFFTypeSelect(e) {
      console.log(e);
    }
  }
}
</script>