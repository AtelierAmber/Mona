package com.github.atelieramber.mona.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TestBlock extends Block{

	public TestBlock() {
		super(Block.Properties.create(Material.WOOL)
				.hardnessAndResistance(1.0f)
				.noDrops()
				);
	}
	
}
