<template>
  <tr>
    <td>
      <strong>{{ keyCode }}</strong><br>
      <small>{{title}}</small>
    </td>
    <td>-</td>
    <td>{{ (lastRequested === undefined || lastRequested === null) ? 'Never' : lastRequested }}</td>
    <td>
      <Toggle v-bind:checked="targeting"></Toggle>
    </td>
  </tr>
</template>
<script>
import Toggle from "@/components/Toggle";

export default {
  name: 'FeatureFlagView',
  components: {Toggle},
  props: ['title', 'keyCode', 'targeting', 'lastRequested']
}
</script>