package com.ashok.data.entity.response.pokemondetail


import com.google.gson.annotations.SerializedName

data class PokemonDetailEntity(
    @SerializedName("base_happiness")
    val baseHappiness: Int? = 0,
    @SerializedName("capture_rate")
    val captureRate: Int? = 0,
    @SerializedName("color")
    val color: Color? = null ,
    @SerializedName("egg_groups")
    val eggGroups: List<EggGroup>? = null,
    @SerializedName("evolution_chain")
    val evolutionChain: EvolutionChain? = null,
    @SerializedName("evolves_from_species")
    val evolvesFromSpecies: Any? = null,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntry>? = null,
    @SerializedName("form_descriptions")
    val formDescriptions: List<Any>? = null,
    @SerializedName("forms_switchable")
    val formsSwitchable: Boolean? = null,
    @SerializedName("gender_rate")
    val genderRate: Int? = null,
    @SerializedName("genera")
    val genera: List<Genera>? = null,
    @SerializedName("generation")
    val generation: Generation? = null,
    @SerializedName("growth_rate")
    val growthRate: GrowthRate? = null,
    @SerializedName("habitat")
    val habitat: Habitat? = null,
    @SerializedName("has_gender_differences")
    val hasGenderDifferences: Boolean? = null,
    @SerializedName("hatch_counter")
    val hatchCounter: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("is_baby")
    val isBaby: Boolean? = null,
    @SerializedName("is_legendary")
    val isLegendary: Boolean? = null,
    @SerializedName("is_mythical")
    val isMythical: Boolean? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("names")
    val names: List<Name>? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("pal_park_encounters")
    val palParkEncounters: List<PalParkEncounter>? = null,
    @SerializedName("pokedex_numbers")
    val pokedexNumbers: List<PokedexNumber>? = null,
    @SerializedName("shape")
    val shape: Shape? = null,
    @SerializedName("varieties")
    val varieties: List<Variety>? = null
)