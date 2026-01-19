<template>
    <main>
        <div class="outerNamePickerContainer">
            <div class="namePickerContainer">
                <select name="schoolClasses" id="schoolClasses" class="namePickerChild" v-bind="selectedSchoolClass">
                    <option v-for="schoolClass in schoolClasses">{{ schoolClass }}</option>
                </select>
                <br>

                <input type="number" name="nameCount" id="nameCount" class="namePickerChild" v-bind="nameCount">
                <br>

                <button @click="onPick" class="pickButton namePickerChild">Pick</button>
            </div>
        </div>
    </main>
</template>

<script setup>
import "~/assets/css/style.css"

const nameCount = ref(0)
const selectedSchoolClass = ref("")
const schoolClasses = ref(["class1", "class2"])

const onPick = () => {
    return ""
}
</script>