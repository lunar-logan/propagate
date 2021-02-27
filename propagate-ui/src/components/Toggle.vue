<template>
  <div v-on:click="handleClick" style="cursor: pointer;">
    <div v-bind:class="{'holder-unchecked': !isChecked, 'holder-checked': isChecked}">
      <div class="has-background-white"
           style="width: 15px; height: 15px; border-radius: 15px; position: absolute; right: 3px;"
           v-if="isChecked"></div>
      <div class="has-background-white"
           style="width: 15px; height: 15px; border-radius: 15px; position: absolute; left: 3px;" v-else></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Toggle',
  props: ['checked'],
  data() {
    return {
      isChecked: this.checked
    }
  },
  methods: {
    handleClick() {
      this.isChecked = !this.isChecked;
    }
  }
}
</script>