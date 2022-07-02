package me.fan87.spookysky.client.mapping.impl.item


import me.fan87.spookysky.client.mapping.*


object MapItems : ClassMapping<Items>() {
    override fun getWrapperClass(): Class<Items> {
        return Items::class.java
    }

    override val humanReadableName: String
        get() = "Items"

    val iron_pickaxe = ItemsMapping("iron_pickaxe")
    val iron_shovel = ItemsMapping("iron_shovel")
    val iron_axe = ItemsMapping("iron_axe")
    val flint_and_steel = ItemsMapping("flint_and_steel")
    val apple = ItemsMapping("apple")
    val bow = ItemsMapping("bow")
    val arrow = ItemsMapping("arrow")
    val coal = ItemsMapping("coal")
    val diamond = ItemsMapping("diamond")
    val iron_ingot = ItemsMapping("iron_ingot")
    val gold_ingot = ItemsMapping("gold_ingot")
    val iron_sword = ItemsMapping("iron_sword")
    val wooden_sword = ItemsMapping("wooden_sword")
    val wooden_shovel = ItemsMapping("wooden_shovel")
    val wooden_pickaxe = ItemsMapping("wooden_pickaxe")
    val wooden_axe = ItemsMapping("wooden_axe")
    val stone_sword = ItemsMapping("stone_sword")
    val stone_shovel = ItemsMapping("stone_shovel")
    val stone_pickaxe = ItemsMapping("stone_pickaxe")
    val stone_axe = ItemsMapping("stone_axe")
    val diamond_sword = ItemsMapping("diamond_sword")
    val diamond_shovel = ItemsMapping("diamond_shovel")
    val diamond_pickaxe = ItemsMapping("diamond_pickaxe")
    val diamond_axe = ItemsMapping("diamond_axe")
    val stick = ItemsMapping("stick")
    val bowl = ItemsMapping("bowl")
    val mushroom_stew = ItemsMapping("mushroom_stew")
    val golden_sword = ItemsMapping("golden_sword")
    val golden_shovel = ItemsMapping("golden_shovel")
    val golden_pickaxe = ItemsMapping("golden_pickaxe")
    val golden_axe = ItemsMapping("golden_axe")
    val string = ItemsMapping("string")
    val feather = ItemsMapping("feather")
    val gunpowder = ItemsMapping("gunpowder")
    val wooden_hoe = ItemsMapping("wooden_hoe")
    val stone_hoe = ItemsMapping("stone_hoe")
    val iron_hoe = ItemsMapping("iron_hoe")
    val diamond_hoe = ItemsMapping("diamond_hoe")
    val golden_hoe = ItemsMapping("golden_hoe")
    val wheat_seeds = ItemsMapping("wheat_seeds")
    val wheat = ItemsMapping("wheat")
    val bread = ItemsMapping("bread")
    val leather_helmet = ItemsMapping("leather_helmet")
    val leather_chestplate = ItemsMapping("leather_chestplate")
    val leather_leggings = ItemsMapping("leather_leggings")
    val leather_boots = ItemsMapping("leather_boots")
    val chainmail_helmet = ItemsMapping("chainmail_helmet")
    val chainmail_chestplate = ItemsMapping("chainmail_chestplate")
    val chainmail_leggings = ItemsMapping("chainmail_leggings")
    val chainmail_boots = ItemsMapping("chainmail_boots")
    val iron_helmet = ItemsMapping("iron_helmet")
    val iron_chestplate = ItemsMapping("iron_chestplate")
    val iron_leggings = ItemsMapping("iron_leggings")
    val iron_boots = ItemsMapping("iron_boots")
    val diamond_helmet = ItemsMapping("diamond_helmet")
    val diamond_chestplate = ItemsMapping("diamond_chestplate")
    val diamond_leggings = ItemsMapping("diamond_leggings")
    val diamond_boots = ItemsMapping("diamond_boots")
    val golden_helmet = ItemsMapping("golden_helmet")
    val golden_chestplate = ItemsMapping("golden_chestplate")
    val golden_leggings = ItemsMapping("golden_leggings")
    val golden_boots = ItemsMapping("golden_boots")
    val flint = ItemsMapping("flint")
    val porkchop = ItemsMapping("porkchop")
    val cooked_porkchop = ItemsMapping("cooked_porkchop")
    val painting = ItemsMapping("painting")
    val golden_apple = ItemsMapping("golden_apple")
    val sign = ItemsMapping("sign")
    val oak_door = ItemsMapping("wooden_door")
    val spruce_door = ItemsMapping("spruce_door")
    val birch_door = ItemsMapping("birch_door")
    val jungle_door = ItemsMapping("jungle_door")
    val acacia_door = ItemsMapping("acacia_door")
    val dark_oak_door = ItemsMapping("dark_oak_door")
    val bucket = ItemsMapping("bucket")
    val water_bucket = ItemsMapping("water_bucket")
    val lava_bucket = ItemsMapping("lava_bucket")
    val minecart = ItemsMapping("minecart")
    val saddle = ItemsMapping("saddle")
    val iron_door = ItemsMapping("iron_door")
    val redstone = ItemsMapping("redstone")
    val snowball = ItemsMapping("snowball")
    val boat = ItemsMapping("boat")
    val leather = ItemsMapping("leather")
    val milk_bucket = ItemsMapping("milk_bucket")
    val brick = ItemsMapping("brick")
    val clay_ball = ItemsMapping("clay_ball")
    val reeds = ItemsMapping("reeds")
    val paper = ItemsMapping("paper")
    val book = ItemsMapping("book")
    val slime_ball = ItemsMapping("slime_ball")
    val chest_minecart = ItemsMapping("chest_minecart")
    val furnace_minecart = ItemsMapping("furnace_minecart")
    val egg = ItemsMapping("egg")
    val compass = ItemsMapping("compass")
    val fishing_rod = ItemsMapping("fishing_rod")
    val clock = ItemsMapping("clock")
    val glowstone_dust = ItemsMapping("glowstone_dust")
    val fish = ItemsMapping("fish")
    val cooked_fish = ItemsMapping("cooked_fish")
    val dye = ItemsMapping("dye")
    val bone = ItemsMapping("bone")
    val sugar = ItemsMapping("sugar")
    val cake = ItemsMapping("cake")
    val bed = ItemsMapping("bed")
    val repeater = ItemsMapping("repeater")
    val cookie = ItemsMapping("cookie")
    val filled_map = ItemsMapping("filled_map")
    val shears = ItemsMapping("shears")
    val melon = ItemsMapping("melon")
    val pumpkin_seeds = ItemsMapping("pumpkin_seeds")
    val melon_seeds = ItemsMapping("melon_seeds")
    val beef = ItemsMapping("beef")
    val cooked_beef = ItemsMapping("cooked_beef")
    val chicken = ItemsMapping("chicken")
    val cooked_chicken = ItemsMapping("cooked_chicken")
    val mutton = ItemsMapping("mutton")
    val cooked_mutton = ItemsMapping("cooked_mutton")
    val rabbit = ItemsMapping("rabbit")
    val cooked_rabbit = ItemsMapping("cooked_rabbit")
    val rabbit_stew = ItemsMapping("rabbit_stew")
    val rabbit_foot = ItemsMapping("rabbit_foot")
    val rabbit_hide = ItemsMapping("rabbit_hide")
    val rotten_flesh = ItemsMapping("rotten_flesh")
    val ender_pearl = ItemsMapping("ender_pearl")
    val blaze_rod = ItemsMapping("blaze_rod")
    val ghast_tear = ItemsMapping("ghast_tear")
    val gold_nugget = ItemsMapping("gold_nugget")
    val nether_wart = ItemsMapping("nether_wart")
    val potionitem = ItemsMapping("potion")
    val glass_bottle = ItemsMapping("glass_bottle")
    val spider_eye = ItemsMapping("spider_eye")
    val fermented_spider_eye = ItemsMapping("fermented_spider_eye")
    val blaze_powder = ItemsMapping("blaze_powder")
    val magma_cream = ItemsMapping("magma_cream")
    val brewing_stand = ItemsMapping("brewing_stand")
    val cauldron = ItemsMapping("cauldron")
    val ender_eye = ItemsMapping("ender_eye")
    val speckled_melon = ItemsMapping("speckled_melon")
    val spawn_egg = ItemsMapping("spawn_egg")
    val experience_bottle = ItemsMapping("experience_bottle")
    val fire_charge = ItemsMapping("fire_charge")
    val writable_book = ItemsMapping("writable_book")
    val written_book = ItemsMapping("written_book")
    val emerald = ItemsMapping("emerald")
    val item_frame = ItemsMapping("item_frame")
    val flower_pot = ItemsMapping("flower_pot")
    val carrot = ItemsMapping("carrot")
    val potato = ItemsMapping("potato")
    val baked_potato = ItemsMapping("baked_potato")
    val poisonous_potato = ItemsMapping("poisonous_potato")
    val map = ItemsMapping("map")
    val golden_carrot = ItemsMapping("golden_carrot")
    val skull = ItemsMapping("skull")
    val carrot_on_a_stick = ItemsMapping("carrot_on_a_stick")
    val nether_star = ItemsMapping("nether_star")
    val pumpkin_pie = ItemsMapping("pumpkin_pie")
    val fireworks = ItemsMapping("fireworks")
    val firework_charge = ItemsMapping("firework_charge")
    val enchanted_book = ItemsMapping("enchanted_book")
    val comparator = ItemsMapping("comparator")
    val netherbrick = ItemsMapping("netherbrick")
    val quartz = ItemsMapping("quartz")
    val tnt_minecart = ItemsMapping("tnt_minecart")
    val hopper_minecart = ItemsMapping("hopper_minecart")
    val armor_stand = ItemsMapping("armor_stand")
    val iron_horse_armor = ItemsMapping("iron_horse_armor")
    val golden_horse_armor = ItemsMapping("golden_horse_armor")
    val diamond_horse_armor = ItemsMapping("diamond_horse_armor")
    val lead = ItemsMapping("lead")
    val name_tag = ItemsMapping("name_tag")
    val command_block_minecart = ItemsMapping("command_block_minecart")
    val record_13 = ItemsMapping("record_13")
    val record_cat = ItemsMapping("record_cat")
    val record_blocks = ItemsMapping("record_blocks")
    val record_chirp = ItemsMapping("record_chirp")
    val record_far = ItemsMapping("record_far")
    val record_mall = ItemsMapping("record_mall")
    val record_mellohi = ItemsMapping("record_mellohi")
    val record_stal = ItemsMapping("record_stal")
    val record_strad = ItemsMapping("record_strad")
    val record_ward = ItemsMapping("record_ward")
    val record_11 = ItemsMapping("record_11")
    val record_wait = ItemsMapping("record_wait")
    val prismarine_shard = ItemsMapping("prismarine_shard")
    val prismarine_crystals = ItemsMapping("prismarine_crystals")
    val banner = ItemsMapping("banner")

}

class ItemsMapping(name: String): FieldMapping<Any, Items>(MapItems, name) {

}


open class Items protected constructor(original: Any) : WrapperClass(original) {
    companion object {
        val iron_shovel: Item get() = MappingsManager.getWrapped(MapItems.iron_shovel.getJavaField().get(null))
        val iron_pickaxe: Item get() = MappingsManager.getWrapped(MapItems.iron_pickaxe.getJavaField().get(null))
        val iron_axe: Item get() = MappingsManager.getWrapped(MapItems.iron_axe.getJavaField().get(null))
        val flint_and_steel: Item get() = MappingsManager.getWrapped(MapItems.flint_and_steel.getJavaField().get(null))
        val apple: Item get() = MappingsManager.getWrapped(MapItems.apple.getJavaField().get(null))
        val bow: Item get() = MappingsManager.getWrapped(MapItems.bow.getJavaField().get(null))
        val arrow: Item get() = MappingsManager.getWrapped(MapItems.arrow.getJavaField().get(null))
        val coal: Item get() = MappingsManager.getWrapped(MapItems.coal.getJavaField().get(null))
        val diamond: Item get() = MappingsManager.getWrapped(MapItems.diamond.getJavaField().get(null))
        val iron_ingot: Item get() = MappingsManager.getWrapped(MapItems.iron_ingot.getJavaField().get(null))
        val gold_ingot: Item get() = MappingsManager.getWrapped(MapItems.gold_ingot.getJavaField().get(null))
        val iron_sword: Item get() = MappingsManager.getWrapped(MapItems.iron_sword.getJavaField().get(null))
        val wooden_sword: Item get() = MappingsManager.getWrapped(MapItems.wooden_sword.getJavaField().get(null))
        val wooden_shovel: Item get() = MappingsManager.getWrapped(MapItems.wooden_shovel.getJavaField().get(null))
        val wooden_pickaxe: Item get() = MappingsManager.getWrapped(MapItems.wooden_pickaxe.getJavaField().get(null))
        val wooden_axe: Item get() = MappingsManager.getWrapped(MapItems.wooden_axe.getJavaField().get(null))
        val stone_sword: Item get() = MappingsManager.getWrapped(MapItems.stone_sword.getJavaField().get(null))
        val stone_shovel: Item get() = MappingsManager.getWrapped(MapItems.stone_shovel.getJavaField().get(null))
        val stone_pickaxe: Item get() = MappingsManager.getWrapped(MapItems.stone_pickaxe.getJavaField().get(null))
        val stone_axe: Item get() = MappingsManager.getWrapped(MapItems.stone_axe.getJavaField().get(null))
        val diamond_sword: Item get() = MappingsManager.getWrapped(MapItems.diamond_sword.getJavaField().get(null))
        val diamond_shovel: Item get() = MappingsManager.getWrapped(MapItems.diamond_shovel.getJavaField().get(null))
        val diamond_pickaxe: Item get() = MappingsManager.getWrapped(MapItems.diamond_pickaxe.getJavaField().get(null))
        val diamond_axe: Item get() = MappingsManager.getWrapped(MapItems.diamond_axe.getJavaField().get(null))
        val stick: Item get() = MappingsManager.getWrapped(MapItems.stick.getJavaField().get(null))
        val bowl: Item get() = MappingsManager.getWrapped(MapItems.bowl.getJavaField().get(null))
        val mushroom_stew: Item get() = MappingsManager.getWrapped(MapItems.mushroom_stew.getJavaField().get(null))
        val golden_sword: Item get() = MappingsManager.getWrapped(MapItems.golden_sword.getJavaField().get(null))
        val golden_shovel: Item get() = MappingsManager.getWrapped(MapItems.golden_shovel.getJavaField().get(null))
        val golden_pickaxe: Item get() = MappingsManager.getWrapped(MapItems.golden_pickaxe.getJavaField().get(null))
        val golden_axe: Item get() = MappingsManager.getWrapped(MapItems.golden_axe.getJavaField().get(null))
        val string: Item get() = MappingsManager.getWrapped(MapItems.string.getJavaField().get(null))
        val feather: Item get() = MappingsManager.getWrapped(MapItems.feather.getJavaField().get(null))
        val gunpowder: Item get() = MappingsManager.getWrapped(MapItems.gunpowder.getJavaField().get(null))
        val wooden_hoe: Item get() = MappingsManager.getWrapped(MapItems.wooden_hoe.getJavaField().get(null))
        val stone_hoe: Item get() = MappingsManager.getWrapped(MapItems.stone_hoe.getJavaField().get(null))
        val iron_hoe: Item get() = MappingsManager.getWrapped(MapItems.iron_hoe.getJavaField().get(null))
        val diamond_hoe: Item get() = MappingsManager.getWrapped(MapItems.diamond_hoe.getJavaField().get(null))
        val golden_hoe: Item get() = MappingsManager.getWrapped(MapItems.golden_hoe.getJavaField().get(null))
        val wheat_seeds: Item get() = MappingsManager.getWrapped(MapItems.wheat_seeds.getJavaField().get(null))
        val wheat: Item get() = MappingsManager.getWrapped(MapItems.wheat.getJavaField().get(null))
        val bread: Item get() = MappingsManager.getWrapped(MapItems.bread.getJavaField().get(null))
        val leather_helmet: Item get() = MappingsManager.getWrapped(MapItems.leather_helmet.getJavaField().get(null))
        val leather_chestplate: Item get() = MappingsManager.getWrapped(MapItems.leather_chestplate.getJavaField().get(null))
        val leather_leggings: Item get() = MappingsManager.getWrapped(MapItems.leather_leggings.getJavaField().get(null))
        val leather_boots: Item get() = MappingsManager.getWrapped(MapItems.leather_boots.getJavaField().get(null))
        val chainmail_helmet: Item get() = MappingsManager.getWrapped(MapItems.chainmail_helmet.getJavaField().get(null))
        val chainmail_chestplate: Item get() = MappingsManager.getWrapped(MapItems.chainmail_chestplate.getJavaField().get(null))
        val chainmail_leggings: Item get() = MappingsManager.getWrapped(MapItems.chainmail_leggings.getJavaField().get(null))
        val chainmail_boots: Item get() = MappingsManager.getWrapped(MapItems.chainmail_boots.getJavaField().get(null))
        val iron_helmet: Item get() = MappingsManager.getWrapped(MapItems.iron_helmet.getJavaField().get(null))
        val iron_chestplate: Item get() = MappingsManager.getWrapped(MapItems.iron_chestplate.getJavaField().get(null))
        val iron_leggings: Item get() = MappingsManager.getWrapped(MapItems.iron_leggings.getJavaField().get(null))
        val iron_boots: Item get() = MappingsManager.getWrapped(MapItems.iron_boots.getJavaField().get(null))
        val diamond_helmet: Item get() = MappingsManager.getWrapped(MapItems.diamond_helmet.getJavaField().get(null))
        val diamond_chestplate: Item get() = MappingsManager.getWrapped(MapItems.diamond_chestplate.getJavaField().get(null))
        val diamond_leggings: Item get() = MappingsManager.getWrapped(MapItems.diamond_leggings.getJavaField().get(null))
        val diamond_boots: Item get() = MappingsManager.getWrapped(MapItems.diamond_boots.getJavaField().get(null))
        val golden_helmet: Item get() = MappingsManager.getWrapped(MapItems.golden_helmet.getJavaField().get(null))
        val golden_chestplate: Item get() = MappingsManager.getWrapped(MapItems.golden_chestplate.getJavaField().get(null))
        val golden_leggings: Item get() = MappingsManager.getWrapped(MapItems.golden_leggings.getJavaField().get(null))
        val golden_boots: Item get() = MappingsManager.getWrapped(MapItems.golden_boots.getJavaField().get(null))
        val flint: Item get() = MappingsManager.getWrapped(MapItems.flint.getJavaField().get(null))
        val porkchop: Item get() = MappingsManager.getWrapped(MapItems.porkchop.getJavaField().get(null))
        val cooked_porkchop: Item get() = MappingsManager.getWrapped(MapItems.cooked_porkchop.getJavaField().get(null))
        val painting: Item get() = MappingsManager.getWrapped(MapItems.painting.getJavaField().get(null))
        val golden_apple: Item get() = MappingsManager.getWrapped(MapItems.golden_apple.getJavaField().get(null))
        val sign: Item get() = MappingsManager.getWrapped(MapItems.sign.getJavaField().get(null))
        val oak_door: Item get() = MappingsManager.getWrapped(MapItems.oak_door.getJavaField().get(null))
        val spruce_door: Item get() = MappingsManager.getWrapped(MapItems.spruce_door.getJavaField().get(null))
        val birch_door: Item get() = MappingsManager.getWrapped(MapItems.birch_door.getJavaField().get(null))
        val jungle_door: Item get() = MappingsManager.getWrapped(MapItems.jungle_door.getJavaField().get(null))
        val acacia_door: Item get() = MappingsManager.getWrapped(MapItems.acacia_door.getJavaField().get(null))
        val dark_oak_door: Item get() = MappingsManager.getWrapped(MapItems.dark_oak_door.getJavaField().get(null))
        val bucket: Item get() = MappingsManager.getWrapped(MapItems.bucket.getJavaField().get(null))
        val water_bucket: Item get() = MappingsManager.getWrapped(MapItems.water_bucket.getJavaField().get(null))
        val lava_bucket: Item get() = MappingsManager.getWrapped(MapItems.lava_bucket.getJavaField().get(null))
        val minecart: Item get() = MappingsManager.getWrapped(MapItems.minecart.getJavaField().get(null))
        val saddle: Item get() = MappingsManager.getWrapped(MapItems.saddle.getJavaField().get(null))
        val iron_door: Item get() = MappingsManager.getWrapped(MapItems.iron_door.getJavaField().get(null))
        val redstone: Item get() = MappingsManager.getWrapped(MapItems.redstone.getJavaField().get(null))
        val snowball: Item get() = MappingsManager.getWrapped(MapItems.snowball.getJavaField().get(null))
        val boat: Item get() = MappingsManager.getWrapped(MapItems.boat.getJavaField().get(null))
        val leather: Item get() = MappingsManager.getWrapped(MapItems.leather.getJavaField().get(null))
        val milk_bucket: Item get() = MappingsManager.getWrapped(MapItems.milk_bucket.getJavaField().get(null))
        val brick: Item get() = MappingsManager.getWrapped(MapItems.brick.getJavaField().get(null))
        val clay_ball: Item get() = MappingsManager.getWrapped(MapItems.clay_ball.getJavaField().get(null))
        val reeds: Item get() = MappingsManager.getWrapped(MapItems.reeds.getJavaField().get(null))
        val paper: Item get() = MappingsManager.getWrapped(MapItems.paper.getJavaField().get(null))
        val book: Item get() = MappingsManager.getWrapped(MapItems.book.getJavaField().get(null))
        val slime_ball: Item get() = MappingsManager.getWrapped(MapItems.slime_ball.getJavaField().get(null))
        val chest_minecart: Item get() = MappingsManager.getWrapped(MapItems.chest_minecart.getJavaField().get(null))
        val furnace_minecart: Item get() = MappingsManager.getWrapped(MapItems.furnace_minecart.getJavaField().get(null))
        val egg: Item get() = MappingsManager.getWrapped(MapItems.egg.getJavaField().get(null))
        val compass: Item get() = MappingsManager.getWrapped(MapItems.compass.getJavaField().get(null))
        val fishing_rod: Item get() = MappingsManager.getWrapped(MapItems.fishing_rod.getJavaField().get(null))
        val clock: Item get() = MappingsManager.getWrapped(MapItems.clock.getJavaField().get(null))
        val glowstone_dust: Item get() = MappingsManager.getWrapped(MapItems.glowstone_dust.getJavaField().get(null))
        val fish: Item get() = MappingsManager.getWrapped(MapItems.fish.getJavaField().get(null))
        val cooked_fish: Item get() = MappingsManager.getWrapped(MapItems.cooked_fish.getJavaField().get(null))
        val dye: Item get() = MappingsManager.getWrapped(MapItems.dye.getJavaField().get(null))
        val bone: Item get() = MappingsManager.getWrapped(MapItems.bone.getJavaField().get(null))
        val sugar: Item get() = MappingsManager.getWrapped(MapItems.sugar.getJavaField().get(null))
        val cake: Item get() = MappingsManager.getWrapped(MapItems.cake.getJavaField().get(null))
        val bed: Item get() = MappingsManager.getWrapped(MapItems.bed.getJavaField().get(null))
        val repeater: Item get() = MappingsManager.getWrapped(MapItems.repeater.getJavaField().get(null))
        val cookie: Item get() = MappingsManager.getWrapped(MapItems.cookie.getJavaField().get(null))
        val filled_map: Item get() = MappingsManager.getWrapped(MapItems.filled_map.getJavaField().get(null))
        val shears: Item get() = MappingsManager.getWrapped(MapItems.shears.getJavaField().get(null))
        val melon: Item get() = MappingsManager.getWrapped(MapItems.melon.getJavaField().get(null))
        val pumpkin_seeds: Item get() = MappingsManager.getWrapped(MapItems.pumpkin_seeds.getJavaField().get(null))
        val melon_seeds: Item get() = MappingsManager.getWrapped(MapItems.melon_seeds.getJavaField().get(null))
        val beef: Item get() = MappingsManager.getWrapped(MapItems.beef.getJavaField().get(null))
        val cooked_beef: Item get() = MappingsManager.getWrapped(MapItems.cooked_beef.getJavaField().get(null))
        val chicken: Item get() = MappingsManager.getWrapped(MapItems.chicken.getJavaField().get(null))
        val cooked_chicken: Item get() = MappingsManager.getWrapped(MapItems.cooked_chicken.getJavaField().get(null))
        val mutton: Item get() = MappingsManager.getWrapped(MapItems.mutton.getJavaField().get(null))
        val cooked_mutton: Item get() = MappingsManager.getWrapped(MapItems.cooked_mutton.getJavaField().get(null))
        val rabbit: Item get() = MappingsManager.getWrapped(MapItems.rabbit.getJavaField().get(null))
        val cooked_rabbit: Item get() = MappingsManager.getWrapped(MapItems.cooked_rabbit.getJavaField().get(null))
        val rabbit_stew: Item get() = MappingsManager.getWrapped(MapItems.rabbit_stew.getJavaField().get(null))
        val rabbit_foot: Item get() = MappingsManager.getWrapped(MapItems.rabbit_foot.getJavaField().get(null))
        val rabbit_hide: Item get() = MappingsManager.getWrapped(MapItems.rabbit_hide.getJavaField().get(null))
        val rotten_flesh: Item get() = MappingsManager.getWrapped(MapItems.rotten_flesh.getJavaField().get(null))
        val ender_pearl: Item get() = MappingsManager.getWrapped(MapItems.ender_pearl.getJavaField().get(null))
        val blaze_rod: Item get() = MappingsManager.getWrapped(MapItems.blaze_rod.getJavaField().get(null))
        val ghast_tear: Item get() = MappingsManager.getWrapped(MapItems.ghast_tear.getJavaField().get(null))
        val gold_nugget: Item get() = MappingsManager.getWrapped(MapItems.gold_nugget.getJavaField().get(null))
        val nether_wart: Item get() = MappingsManager.getWrapped(MapItems.nether_wart.getJavaField().get(null))
        val potionitem: Item get() = MappingsManager.getWrapped(MapItems.potionitem.getJavaField().get(null))
        val glass_bottle: Item get() = MappingsManager.getWrapped(MapItems.glass_bottle.getJavaField().get(null))
        val spider_eye: Item get() = MappingsManager.getWrapped(MapItems.spider_eye.getJavaField().get(null))
        val fermented_spider_eye: Item get() = MappingsManager.getWrapped(MapItems.fermented_spider_eye.getJavaField().get(null))
        val blaze_powder: Item get() = MappingsManager.getWrapped(MapItems.blaze_powder.getJavaField().get(null))
        val magma_cream: Item get() = MappingsManager.getWrapped(MapItems.magma_cream.getJavaField().get(null))
        val brewing_stand: Item get() = MappingsManager.getWrapped(MapItems.brewing_stand.getJavaField().get(null))
        val cauldron: Item get() = MappingsManager.getWrapped(MapItems.cauldron.getJavaField().get(null))
        val ender_eye: Item get() = MappingsManager.getWrapped(MapItems.ender_eye.getJavaField().get(null))
        val speckled_melon: Item get() = MappingsManager.getWrapped(MapItems.speckled_melon.getJavaField().get(null))
        val spawn_egg: Item get() = MappingsManager.getWrapped(MapItems.spawn_egg.getJavaField().get(null))
        val experience_bottle: Item get() = MappingsManager.getWrapped(MapItems.experience_bottle.getJavaField().get(null))
        val fire_charge: Item get() = MappingsManager.getWrapped(MapItems.fire_charge.getJavaField().get(null))
        val writable_book: Item get() = MappingsManager.getWrapped(MapItems.writable_book.getJavaField().get(null))
        val written_book: Item get() = MappingsManager.getWrapped(MapItems.written_book.getJavaField().get(null))
        val emerald: Item get() = MappingsManager.getWrapped(MapItems.emerald.getJavaField().get(null))
        val item_frame: Item get() = MappingsManager.getWrapped(MapItems.item_frame.getJavaField().get(null))
        val flower_pot: Item get() = MappingsManager.getWrapped(MapItems.flower_pot.getJavaField().get(null))
        val carrot: Item get() = MappingsManager.getWrapped(MapItems.carrot.getJavaField().get(null))
        val potato: Item get() = MappingsManager.getWrapped(MapItems.potato.getJavaField().get(null))
        val baked_potato: Item get() = MappingsManager.getWrapped(MapItems.baked_potato.getJavaField().get(null))
        val poisonous_potato: Item get() = MappingsManager.getWrapped(MapItems.poisonous_potato.getJavaField().get(null))
        val map: Item get() = MappingsManager.getWrapped(MapItems.map.getJavaField().get(null))
        val golden_carrot: Item get() = MappingsManager.getWrapped(MapItems.golden_carrot.getJavaField().get(null))
        val skull: Item get() = MappingsManager.getWrapped(MapItems.skull.getJavaField().get(null))
        val carrot_on_a_stick: Item get() = MappingsManager.getWrapped(MapItems.carrot_on_a_stick.getJavaField().get(null))
        val nether_star: Item get() = MappingsManager.getWrapped(MapItems.nether_star.getJavaField().get(null))
        val pumpkin_pie: Item get() = MappingsManager.getWrapped(MapItems.pumpkin_pie.getJavaField().get(null))
        val fireworks: Item get() = MappingsManager.getWrapped(MapItems.fireworks.getJavaField().get(null))
        val firework_charge: Item get() = MappingsManager.getWrapped(MapItems.firework_charge.getJavaField().get(null))
        val enchanted_book: Item get() = MappingsManager.getWrapped(MapItems.enchanted_book.getJavaField().get(null))
        val comparator: Item get() = MappingsManager.getWrapped(MapItems.comparator.getJavaField().get(null))
        val netherbrick: Item get() = MappingsManager.getWrapped(MapItems.netherbrick.getJavaField().get(null))
        val quartz: Item get() = MappingsManager.getWrapped(MapItems.quartz.getJavaField().get(null))
        val tnt_minecart: Item get() = MappingsManager.getWrapped(MapItems.tnt_minecart.getJavaField().get(null))
        val hopper_minecart: Item get() = MappingsManager.getWrapped(MapItems.hopper_minecart.getJavaField().get(null))
        val armor_stand: Item get() = MappingsManager.getWrapped(MapItems.armor_stand.getJavaField().get(null))
        val iron_horse_armor: Item get() = MappingsManager.getWrapped(MapItems.iron_horse_armor.getJavaField().get(null))
        val golden_horse_armor: Item get() = MappingsManager.getWrapped(MapItems.golden_horse_armor.getJavaField().get(null))
        val diamond_horse_armor: Item get() = MappingsManager.getWrapped(MapItems.diamond_horse_armor.getJavaField().get(null))
        val lead: Item get() = MappingsManager.getWrapped(MapItems.lead.getJavaField().get(null))
        val name_tag: Item get() = MappingsManager.getWrapped(MapItems.name_tag.getJavaField().get(null))
        val command_block_minecart: Item get() = MappingsManager.getWrapped(MapItems.command_block_minecart.getJavaField().get(null))
        val record_13: Item get() = MappingsManager.getWrapped(MapItems.record_13.getJavaField().get(null))
        val record_cat: Item get() = MappingsManager.getWrapped(MapItems.record_cat.getJavaField().get(null))
        val record_blocks: Item get() = MappingsManager.getWrapped(MapItems.record_blocks.getJavaField().get(null))
        val record_chirp: Item get() = MappingsManager.getWrapped(MapItems.record_chirp.getJavaField().get(null))
        val record_far: Item get() = MappingsManager.getWrapped(MapItems.record_far.getJavaField().get(null))
        val record_mall: Item get() = MappingsManager.getWrapped(MapItems.record_mall.getJavaField().get(null))
        val record_mellohi: Item get() = MappingsManager.getWrapped(MapItems.record_mellohi.getJavaField().get(null))
        val record_stal: Item get() = MappingsManager.getWrapped(MapItems.record_stal.getJavaField().get(null))
        val record_strad: Item get() = MappingsManager.getWrapped(MapItems.record_strad.getJavaField().get(null))
        val record_ward: Item get() = MappingsManager.getWrapped(MapItems.record_ward.getJavaField().get(null))
        val record_11: Item get() = MappingsManager.getWrapped(MapItems.record_11.getJavaField().get(null))
        val record_wait: Item get() = MappingsManager.getWrapped(MapItems.record_wait.getJavaField().get(null))
        val prismarine_shard: Item get() = MappingsManager.getWrapped(MapItems.prismarine_shard.getJavaField().get(null))
        val prismarine_crystals: Item get() = MappingsManager.getWrapped(MapItems.prismarine_crystals.getJavaField().get(null))
        val banner: Item get() = MappingsManager.getWrapped(MapItems.banner.getJavaField().get(null))
    }
}
