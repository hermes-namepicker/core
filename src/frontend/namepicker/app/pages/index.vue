<template>
    <div class="namePickerContainer">
        <select name="schoolClasses" id="schoolClasses" v-bind="selectedSchoolClass">
            <option v-for="schoolClass in schoolClasses">{{ schoolClass.name }}</option>
        </select>
        <br>

        <input type="number" name="nameCount" id="nameCount" v-bind="nameCount">
        <br>

        <button @click="onPick">Picken</button>
    </div>
</template>

<script>
import "~/assets/css/style.css"

const nameCount = ref(0)
const selectedSchoolClass = ref("")
const schoolClasses = ref(["class1", "class2"])

const onPick = () => {
    return ""
}
</script>