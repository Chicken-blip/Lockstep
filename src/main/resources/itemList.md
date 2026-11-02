Here are 25 sets and 15 standalone items, with contents and sell values. Values are *suggestions* — you'll tune them once you see what a heist actually nets.

Two conventions I used, worth knowing:
- **Piece value** = what one item sells for alone.
- **Set value** = what the complete set sells for (always above the sum of piece values — that's the incentive to complete).

### 25 Sets

| #     | Set name | Contents | Piece value (each) | Set value |
|-------|---|---|---|---|
| **1** | Candelabra Pair | Candelabra A, Candelabra B | 10 | 50 |
| 2     | Van Gogh Study | Sunflowers Study, Irises Study, Wheatfield Study | 40 | 200 |
| 3     | Roman Hoard | Silver Denarius, Gold Aureus, Bronze Sestertius | 15 | 70 |
| **4** | Royal Regalia | Crown, Scepter, Orb | 60 | 300 |
| 5     | Armor Set | Cuirass, Greaves, Helmet | 25 | 120 |
| 6     | Tea Service | Teapot, Creamer, Sugar Bowl | 12 | 55 |
| 7     | Jade Collection | Jade Bi Disc, Jade Pendant, Jade Bowl | 30 | 150 |
| 8     | Illuminated Pages | Vellum Page I, Vellum Page II, Vellum Page III | 20 | 95 |
| 9     | Astrolabe Set | Astrolabe, Compass, Sundial | 18 | 80 |
| 10    | Fabergé Eggs | Egg I, Egg II, Egg III | 50 | 250 |
| 11    | Bronze Busts | Bust of Senator, Bust of Emperor | 35 | 100 |
| 12    | Silverware | Serving Fork, Serving Spoon, Ladle | 8 | 35 |
| **13** | Antique Maps | Map of the New World, Map of the Old World | 22 | 70 |
| **14** | Samurai Kit | Katana, Wakizashi, Tanto | 45 | 220 |
| 15    | Egyptian Tomb | Scarab Amulet, Canopic Jar, Funerary Mask | 55 | 280 |
| 16    | Renaissance Sketches | Da Vinci Sketch, Michelangelo Sketch, Raphael Sketch | 48 | 240 |
| 17    | Chess Set | White King, Black King, Board | 30 | 140 |
| 18    | Russian Icons | Icon of Saint I, Icon of Saint II | 28 | 90 |
| 19    | Pearl Necklace | Pearl Strand, Pearl Earrings, Pearl Brooch | 16 | 70 |
| 20    | Tribal Masks | Mask of the Sun, Mask of the Moon, Mask of the Rain | 24 | 115 |
| 21    | Alchemical Kit | Alembic, Mortar and Pestle, Grimoire | 32 | 150 |
| 22    | Crown Jewels | Ruby Ring, Sapphire Ring, Emerald Ring | 40 | 180 |
| 23    | Viking Hoard | Axe Head, Silver Torc, Rune Stone | 26 | 125 |
| 24    | Porcelain Figures | Dancer, Musician, Poet | 14 | 65 |
| 25    | Astronomical Clock | Clock Face, Clock Movement, Pendulum | 38 | 175 |

### 15 Standalone Items

| #      | Item name | Room flavor | Piece value |
|--------|---|---|---|
| 1      | The Restoration Painting | RESTORATION (mid-restoration) | 60 |
| **2**  | Office Cash Box | SECURITY_OFFICE | 25 |
| 3      | Deposit Slips | STORAGE | 5 |
| 4      | Brass Fittings | STORAGE | 6 |
| 5      | Packing Crate | STORAGE | 4 |
| 6      | Marble Fragment | STORAGE | 8 |
| 7      | Velvet Rope | LOBBY | 3 |
| 8      | Visitor Register | LOBBY | 2 |
| **9**  | Donation Box | LOBBY | 12 |
| 10     | Gallery Placard | EAST_GALLERY | 2 |
| 11     | Display Case Lock | WEST_GALLERY | 5 |
| 12     | Postcard Rack | LOBBY | 3 |
| 13     | Museum Keycard | SECURITY_OFFICE | 15 |
| 14     | Tool Cabinet | RESTORATION | 18 |
| **15** | Main Score (safe_contents) | VAULT | — (flag) |
| **16** | Wooden Pallet | STORAGE | 3 | 1.0 |
| 17     | Canvas Roll | STORAGE | 7 | 1.0 |
| 18     | Burlap Sack | STORAGE | 4 | 1.0 |
| **19** | Iron Key Ring | STORAGE | 9 | 1.0 |
| 20     | Empty Frame | STORAGE | 6 | 1.0 |
| 21     | Cardboard Tube | STORAGE | 2 | 1.0 |
| 22     | Loose Marbles | STORAGE | 3 | 1.0 |
| 23     | Wooden Crate | STORAGE | 5 | 1.0 |
| 24     | Packing Foam | STORAGE | 1 | 1.0 |
| 25     | Old Ledger | STORAGE | 11 | 1.0 |
| **26** | Linen Wrapping | STORAGE | 2 | 1.0 |
| **27** | Brass Lamp | STORAGE | 14 | 1.0 |
| 28     | Stone Bust Fragment | STORAGE | 16 | 1.0 |
| 29     | Tarnished Mirror | STORAGE | 13 | 1.0 |
| 30     | Ceramic Vase | STORAGE | 10 | 1.0 |
| 31     | Coins Jar | STORAGE | 8 | 1.0 |
| 32     | Rusted Tools | STORAGE | 7 | 1.0 |
| 33     | Velvet Cushion | LOBBY | 5 | 1.0 |
| 34     | Framed Photo | LOBBY | 4 | 1.0 |
| 35     | Desk Ornament | SECURITY_OFFICE | 12 | 1.0 |

### A few notes

- **The set values scale.** Cheap sets (silverware, 35) sit alongside expensive ones (Royal Regalia, 300). That gives the museum's loot variety without every item feeling the same.
- **The standalone items are mostly low-value junk** — that's deliberate. "Bulk loot" in STORAGE should be *many small grabs* that add up, not a single high-value item. The temptation is that you could carry out five crates' worth if you had time.
- **The Restoration Painting is the stand-out** (60) — the off-route high-value item. Worth a detour, but it's the riskiest grab in the museum.
- **The Main Score** shouldn't be in the standalone list as a normal item — it's the safe contents, and it's already a special case.


# Loot + Set Categories
## Cash / liquid valuable

| id | name | grabCost | pieceValue |
|---|---|---|---|
| till_cash | Till Cash | 1 | 8 |
| tip_jar | Tip Jar | 1 | 4 |
| small_float | Small Float | 1 | 6 |
| cash_drawer | Cash Drawer | 1 | 12 |
| petty_cash_tin | Petty Cash Tin | 1 | 5 |
| deposit_bag | Deposit Bag | 1 | 15 |
| rolled_coins | Rolled Coins | 1 | 7 |
| scratch_cards | Scratch Cards | 1 | 9 |
| desk_wallet | Desk Wallet | 1 | 14 |
| safe_petty_cash | Safe Petty Cash | 1 | 18 |
| expense_envelope | Expense Envelope | 1 | 16 |
| holiday_fund | Holiday Fund | 1 | 20 |
| loose_notes | Loose Notes | 1 | 10 |
| petty_cash_box | Petty Cash Box | 2 | 22 |
| invoice_payout | Invoice Payout | 1 | 25 |
| bar_till | Bar Till | 1 | 12 |
| coat_check_cash | Coat Check Cash | 1 | 9 |
| door_takings | Door Takings | 1 | 24 |
| raffle_jar | Raffle Jar | 1 | 6 |
| vendor_cashbox | Vendor Cashbox | 2 | 28 |
| coat_check_tips | Coat Check Tips | 1 | 7 |
| manager_float | Manager's Float | 1 | 26 |
| weekend_deposit | Weekend Deposit | 2 | 32 |
| strongbox_cash | Strongbox Cash | 2 | 35 |
| charity_tin | Charity Tin | 1 | 11 |
| staff_wages | Staff Wages | 2 | 38 |
| christmas_club | Christmas Club | 1 | 19 |
| petty_cash_vault | Petty Cash Vault | 2 | 40 |

## Small Valuables (jewelry, watches, precious smalls) — 28 items

| id | name | grabCost | pieceValue |
|---|---|---|---|
| silver_ring | Silver Ring | 1 | 30 |
| gold_band | Gold Band | 1 | 55 |
| signet_ring | Signet Ring | 1 | 70 |
| diamond_solitaire | Diamond Solitaire | 1 | 180 |
| engagement_set | Engagement Set | 1 | 140 |
| wedding_pair | Wedding Pair | 1 | 95 |
| emerald_ring | Emerald Ring | 1 | 150 |
| ruby_cluster | Ruby Cluster Ring | 1 | 120 |
| silver_chain | Silver Chain | 1 | 40 |
| gold_chain | Gold Chain | 1 | 75 |
| pearl_strand | Pearl Strand | 1 | 130 |
| sapphire_pendant | Sapphire Pendant | 1 | 160 |
| diamond_riviere | Diamond Rivière | 1 | 190 |
| locket_gold | Gold Locket | 1 | 60 |
| stud_pair | Diamond Studs | 1 | 90 |
| drop_earrings | Drop Earrings | 1 | 65 |
| charm_bracelet | Charm Bracelet | 1 | 50 |
| tennis_bracelet | Tennis Bracelet | 1 | 110 |
| cufflinks_silver | Silver Cufflinks | 1 | 35 |
| brooch_cameo | Cameo Brooch | 1 | 45 |
| brooch_pearl | Pearl Brooch | 1 | 80 |
| dress_watch | Dress Watch | 1 | 85 |
| gold_pocket_watch | Gold Pocket Watch | 1 | 120 |
| chronograph | Chronograph | 1 | 160 |
| sports_watch | Sports Watch | 1 | 100 |
| vintage_watch | Vintage Watch | 1 | 200 |
| watch_case | Display Watch Case | 2 | 175 |
| gold_coins | Gold Coins | 1 | 95 |
| loose_gems | Loose Gems | 1 | 110 |
| silver_flatware | Silver Flatware | 2 | 60 |
| decorative_snuffbox | Snuffbox | 1 | 70 |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| bridal_set | 3 — `bridal_ring`, `bridal_band`, `bridal_studs` | 120 | Jewelry |
| parure_set | 3 — `parure_necklace`, `parure_bracelet`, `parure_earrings` | 150 | Jewelry |
| gentlemans_set | 2 — `gentlemans_watch`, `gentlemans_cufflinks` | 110 | Watches & Accessories |
| pearl_suite | 3 — `pearl_suite_necklace`, `pearl_suite_earrings`, `pearl_suite_brooch` | 130 | Jewelry |
| signet_collection | 3 — `signet_gold`, `signet_silver`, `signet_onyx` | 120 | Jewelry |
| ruby_riviere | 3 — `ruby_necklace`, `ruby_bracelet`, `ruby_band` | 180 | Jewelry |
| watch_trio | 3 — `trio_watch_a`, `trio_watch_b`, `trio_watch_c` | 200 | Watches & Accessories |

**Set-Piece Individual Prices:**
- `bridal_ring` (50), `bridal_band` (40), `bridal_studs` (30) → sum 120
- `parure_necklace` (60), `parure_bracelet` (45), `parure_earrings` (45) → sum 150
- `gentlemans_watch` (70), `gentlemans_cufflinks` (40) → sum 110
- `pearl_suite_necklace` (50), `pearl_suite_earrings` (40), `pearl_suite_brooch` (40) → sum 130
- `signet_gold` (50), `signet_silver` (35), `signet_onyx` (35) → sum 120
- `ruby_necklace` (80), `ruby_bracelet` (50), `ruby_band` (50) → sum 180
- `trio_watch_a` (60), `trio_watch_b` (75), `trio_watch_c` (65) → sum 200

## Art and display pieces — 30 items

| id | name | grabCost | pieceValue  | set |
|---|---|---|---|----|
| porcelain_figurine | Porcelain Figurine | 1 | 55          | null |
| ivory_cameo | Ivory Cameo | 1 | 65          | null |
| enamel_box | Enamel Box | 1 | 70          | null |
| inkwell_set | Inkwell Set | 1 | 80          | null |
| bronze_statuette | Bronze Statuette | 2 | 95          | null |
| jade_carving | Jade Carving | 1 | 110         | null |
| crystal_orb | Crystal Orb | 1 | 85          | null |
| landscape_painting | Landscape Painting | 2 | 120         | null |
| portrait_miniature | Portrait Miniature | 1 | 90          | null |
| still_life | Still Life | 2 | 140         | null |
| watercolor_sketch | Watercolor Sketch | 1 | 75          | null |
| abstract_canvas | Abstract Canvas | 2 | 180         | null |
| charcoal_study | Charcoal Study | 1 | 85          | null |
| grand_portrait | Grand Portrait | 3 | 320         | null |
| marble_bust | Marble Bust | 3 | 280         | null |
| gilded_mirror | Gilded Mirror | 2 | 220         | null |
| tapestry_fragment | Tapestry Fragment | 2 | 240         | null |
| alabaster_sculpture | Alabaster Sculpture | 3 | 380         | null |
| antique_astrolabe | Antique Astrolabe | 2 | 300         | null |

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_masterpiece | The Masterpiece | 3 | 500 | null | true |
| the_donation | The Donation | 3 | 450 | null | true |
| the_prized_safe_piece | The Prized Safe Piece | 1 | 400 | null | (depends on target) |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| porcelain_birds | 3 — `porcelain_bird_a`, `porcelain_bird_b`, `porcelain_bird_c` | 180 | Display |
| signed_prints | 3 — `signed_print_a`, `signed_print_b`, `signed_print_c` | 220 | Framed |
| the_two_sisters | 2 — `sister_portrait_a`, `sister_portrait_b` | 280 | Framed |
| the_lost_masters | 4 — `lost_master_a`, `lost_master_b`, `lost_master_c`, `lost_master_d` | 600 | Showpiece |
| moon_phases | 4 — `moon_phase_i`, `moon_phase_ii`, `moon_phase_iii`, `moon_phase_iv` | 320 | Display |
| teapot_service | 3 — `teapot`, `sugar_bowl`, `creamer` | 260 | Display |

**Set-Piece Individual Prices:**
- `porcelain_bird_a` (60), `_b` (60), `_c` (60) → sum 180
- `signed_print_a` (70), `_b` (75), `_c` (75) → sum 220
- `sister_portrait_a` (140), `_b` (140) → sum 280
- `lost_master_a` (140), `_b` (150), `_c` (150), `_d` (160) → sum 600
- `moon_phase_i` (75), `_ii` (75), `_iii` (85), `_iv` (85) → sum 320
- `teapot` (100), `sugar_bowl` (80), `creamer` (80) → sum 260

## Heirlooms and antiques

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| silver_spoon | Silver Spoon | 1 | 40 | null |
| silver_gravy_boat | Silver Gravy Boat | 1 | 75 | null |
| silver_candlestick | Silver Candlestick | 1 | 85 | null |
| silver_snuffbox | Silver Snuffbox | 1 | 55 | null |
| silver_tray | Silver Tray | 2 | 110 | null |
| silver_salver | Silver Salver | 2 | 120 | null |
| silver_tea_strainer | Silver Tea Strainer | 1 | 45 | null |
| pocket_watch | Pocket Watch | 1 | 90 | null |
| signet_ring | Signet Ring | 1 | 130 | null |
| tortoiseshell_comb | Tortoiseshell Comb | 1 | 60 | null |
| brass_compass | Brass Compass | 1 | 70 | null |
| snuff_flask | Snuff Flask | 1 | 55 | null |
| pocket_sextant | Pocket Sextant | 2 | 140 | null |
| enamel_locket | Enamel Locket | 1 | 105 | null |
| writing_slope | Writing Slope | 2 | 160 | null |
| walnut_humidor | Walnut Humidor | 2 | 130 | null |
| mahogany_casket | Mahogany Casket | 2 | 210 | null |
| brass_telescope | Brass Telescope | 2 | 180 | null |
| carved_chess_set | Carved Chess Set | 2 | 200 | null |
| antique_barometer | Antique Barometer | 2 | 240 | null |
| inlaid_music_box | Inlaid Music Box | 2 | 150 | null |
| family_bible | Family Bible | 2 | 220 | null |
| gilded_icon | Gilded Icon | 2 | 300 | null |
| ancestral_urn | Ancestral Urn | 3 | 280 | null |
| jeweled_dagger | Jeweled Dagger | 2 | 260 | null |
| hand_illuminated_scroll | Hand-Illuminated Scroll | 1 | 210 | null |
| ivory_fan | Ivory Fan | 1 | 230 | null |

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_family_heirloom | The Family Heirloom | 2 | 400 | null | true |
| the_stolen_crown_jewel | The Stolen Crown Jewel | 1 | 450 | null | true |
| the_ancestral_portrait | The Ancestral Portrait | 3 | 380 | null | true |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_family_silver | 4 — `family_silver_spoon`, `family_silver_fork`, `family_silver_knife`, `family_silver_ladle` | 260 | Silver |
| grandmothers_jewels | 3 — `grandmothers_ring`, `grandmothers_brooch`, `grandmothers_pendant` | 340 | Personal |
| the_tea_service | 4 — `tea_pot`, `tea_cup_a`, `tea_cup_b`, `tea_saucer` | 280 | Silver/tableware |
| the_hunting_set | 3 — `hunting_knife`, `hunting_flask`, `hunting_powder_horn` | 300 | Boxed |
| the_dressing_table | 3 — `hair_brush`, `hand_mirror`, `powder_jar` | 240 | Personal |
| the_ancestral_regalia | 3 — `ancestral_scepter`, `ancestral_ring`, `ancestral_medallion` | 500 | Ceremonial-adjacent |

**Set-Piece Individual Prices:**
- `the_family_silver`: 65 + 65 + 65 + 65 = 260
- `grandmothers_jewels`: 110 + 110 + 120 = 340
- `the_tea_service`: 80 + 60 + 60 + 80 = 280
- `the_hunting_set`: 110 + 90 + 100 = 300
- `the_dressing_table`: 80 + 80 + 80 = 240
- `the_ancestral_regalia`: 180 + 150 + 170 = 500

## Documents and files

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| envelope_marked | Marked Envelope | 1 | 15 | null |
| unsigned_contract | Unsigned Contract | 1 | 20 | null |
| business_card | Business Card | 1 | 8 | null |
| memo_pad | Memo Pad | 1 | 10 | null |
| personal_letter | Personal Letter | 1 | 25 | null |
| holiday_card | Holiday Card | 1 | 6 | null |
| printed_receipt | Printed Receipt | 1 | 12 | null |
| invitation | Invitation | 1 | 9 | null |
| case_file | Case File | 1 | 45 | null |
| contract_folder | Contract Folder | 1 | 60 | null |
| employee_file | Employee File | 1 | 35 | null |
| blueprints | Blueprints | 2 | 85 | null |
| architectural_plan | Architectural Plan | 2 | 75 | null |
| financial_statement | Financial Statement | 1 | 55 | null |
| patent_draft | Patent Draft | 1 | 70 | null |
| legal_brief | Legal Brief | 1 | 40 | null |
| leather_ledger | Leather Ledger | 2 | 120 | null |
| bound_minutes | Bound Minutes | 2 | 100 | null |
| filing_box | Filing Box | 2 | 150 | null |
| locked_strongbox | Locked Strongbox | 2 | 180 | null |
| sealed_archive_box | Sealed Archive Box | 3 | 200 | null |
| family_tree_record | Family Tree Record | 2 | 90 | null |

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_original_will | The Original Will | 1 | 250 | null | true |
| the_secret_formula | The Secret Formula | 1 | 300 | null | true |
| the_dossier | The Dossier | 2 | 350 | null | true |
| the_prototype_spec | The Prototype Specification | 2 | 280 | null | true |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_case_file | 3 — `case_file_evidence`, `case_file_testimony`, `case_file_summary` | 240 | Files |
| the_company_archive | 4 — `annual_report`, `board_minutes`, `share_ledger`, `audit_summary` | 380 | Bound records |
| the_research_papers | 3 — `research_proposal`, `research_notes`, `research_findings` | 300 | Files |
| the_correspondence | 4 — `letter_a`, `letter_b`, `letter_c`, `letter_d` | 160 | Loose documents |
| the_blueprint_set | 3 — `ground_plan`, `elevation_plan`, `detail_plan` | 320 | Files |

**Set-Piece Individual Prices:**
- `the_case_file`: 80 + 80 + 80 = 240
- `the_company_archive`: 90 + 95 + 95 + 100 = 380
- `the_research_papers`: 100 + 100 + 100 = 300
- `the_correspondence`: 40 + 40 + 40 + 40 = 160
- `the_blueprint_set`: 105 + 105 + 110 = 320

## Electronics and prototypes

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| smartphone | Smartphone | 1 | 30 | null |
| encrypted_phone | Encrypted Phone | 1 | 75 | null |
| flash_drive | Flash Drive | 1 | 40 | null |
| external_drive | External Drive | 1 | 55 | null |
| camera_body | Camera Body | 1 | 65 | null |
| lens_kit | Lens Kit | 1 | 80 | null |
| tablet | Tablet | 1 | 45 | null |
| laptop | Laptop | 2 | 100 | null |
| workstation | Workstation | 2 | 140 | null |
| server_drive | Server Drive | 2 | 120 | null |
| router | Router | 2 | 85 | null |
| rack_mount | Rack Mount | 2 | 150 | null |
| test_equipment | Test Equipment | 2 | 130 | null |
| oscilloscope | Oscilloscope | 2 | 110 | null |
| prototype_chip | Prototype Chip | 1 | 200 | null |
| prototype_device | Prototype Device | 3 | 280 | null |
| prototype_module | Prototype Module | 2 | 240 | null |
| rare_component | Rare Component | 1 | 170 | null |
| custom_board | Custom Board | 1 | 160 | null |
| gold_connectors | Gold Connectors | 1 | 150 | null |
| demonstration_unit | Demonstration Unit | 3 | 260 | null |

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_prototype | The Prototype | 3 | 400 | null | true |
| the_algorithm | The Algorithm | 1 | 380 | null | true |
| the_source_code | The Source Code | 1 | 350 | null | true |
| the_working_model | The Working Model | 2 | 420 | null | true |

## Bulk goods

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| cardboard_box | Cardboard Box | 1 | 10 | null |
| produce_crate | Produce Crate | 1 | 12 | null |
| sack_of_grain | Sack of Grain | 1 | 15 | null |
| box_of_canned_goods | Box of Canned Goods | 1 | 18 | null |
| box_of_bottles | Box of Bottles | 1 | 14 | null |
| bundle_of_cloth | Bundle of Cloth | 1 | 20 | null |
| package_stack | Package Stack | 1 | 22 | null |
| bag_of_tools | Bag of Tools | 1 | 25 | null |
| wooden_crate | Wooden Crate | 2 | 30 | null |
| barrel | Barrel | 2 | 35 | null |
| toolbox | Toolbox | 2 | 38 | null |
| sack_of_coffee | Sack of Coffee | 2 | 32 | null |
| box_of_liquor | Box of Liquor | 2 | 45 | null |
| electronic_crate | Electronic Crate | 2 | 48 | null |
| shipment_box | Shipment Box | 2 | 40 | null |
| pallet | Pallet | 3 | 55 | null |
| large_container | Large Container | 3 | 70 | null |
| industrial_crate | Industrial Crate | 3 | 65 | null |
| sealed_shipment | Sealed Shipment | 3 | 75 | null |
| steel_drum | Steel Drum | 3 | 60 | null |
| heavy_machinery_part | Heavy Machinery Part | 3 | 80 | null |
| ledgers_crate | Ledger Crate | 2 | 42 | null |
| stock_manifest | Stock Manifest | 1 | 28 | null |
| delivery_schedule | Delivery Schedule | 1 | 20 | null |
| unmarked_package | Unmarked Package | 2 | 50 | null |
| secure_crate | Secure Crate | 3 | 72 | null |

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_shipment | The Shipment | 3 | 120 | null | true |
| the_contraband | The Contraband | 2 | 150 | null | true |
| the_stolen_goods | The Stolen Goods | 3 | 130 | null | true |

## Weapons

| # | Item | Flavor | Value band |
|---|---|---|---|
| 1 | Antique dueling pistol | Single matched-lock pistol, walnut grip. | Medium |
| 2 | Cavalry saber | Curved blade, officer's pattern. | Medium |
| 3 | Trench knife | Weathered, WWII-era. | Low-medium |
| 4 | Ceremonial dagger | Ornate, non-functional. | Low-medium |
| 5 | Flintlock musket | Wall-hanger, but real. | Medium |
| 6 | Officer's revolver | Engraved, presentation piece. | Medium-high |
| 7 | Pearl-handled derringer | Small, concealable, valuable. | Medium |
| 8 | Naval cutlass | Brass guard, salt-worn. | Medium |
| 9 | Executioner's sword | Grim collector's item. | Medium-high |
| 10 | Hunting rifle | Bolt-action, scoped. | Low-medium |
| 11 | Rapier | Thin, elegant, duel-grade. | Medium |
| 12 | Bayonet | Fixed-blade, historical. | Low |
| 13 | Throwing knives (single) | One of a set, sold loose. | Low |
| 14 | Brass knuckles | Compact, pocketable. | Low |
| 15 | Powder horn | Black-powder era, engraved. | Low-medium |

**Sets**

| # | Set | Members | Set flavor | Set band |
|---|---|---|---|---|
| 1 | The Dueling Pair | Two matched flintlock pistols | Cased presentation set | High |
| 2 | Officer's Kit | Saber + revolver + sash | A single officer's belongings | High |
| 3 | The Collector's Blades | Rapier + dagger + main-gauche | Three-piece fencing set | High |
| 4 | The Trench Set | Knife + bayonet + helmet (as a loot item) | Soldier's kit | Medium |
| 5 | The Gunsmith's Showcase | Rifle + pistol + powder horn | A gunsmith's display | High |
| 6 | The Naval Set | Cutlass + spyglass (loot) + compass (loot) | A captain's kit | High |
| 7 | The Executioner's Tools | Sword + mask (loot) + block (loot, heavy) | Grim, complete | Very high |
| 8 | The Duel Set | Rapier + pistol + glove (loot) | Formal duel kit | Medium |
| 9 | The Throwing Set | Three matched knives | Matched trio | Medium |
| 10 | The Ceremonial Set | Dagger + scabbard (loot) + sash (loot) | Ceremonial regalia | High |
| 11 | The Frontier Set | Rifle + knife + powder horn | Settler's kit | Medium |
| 12 | The Armory Sample | One of each of four small blades | A sampling collection | Medium |
| 13 | The Assassin's Kit | Derringer + dagger + knuckles | Compact, concealable | Medium |
| 14 | The Historical Set | Musket + bayonet + powder horn | Matched era set | High |
| 15 | The Trophy Set | Presentation pistol + engraved case (loot) + medal (loot) | Award set | Very high |

- **Value Bands**: low ≈ 10–20, medium ≈ 30–60, high ≈ 80–150, very high ≈ 200+. Set values should be well above the sum of their pieces, per your design.

## Medical / Pharmaceutical

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| bandage_roll | Bandage Roll | 1 | 6 | null |
| painkiller_bottle | Painkiller Bottle | 1 | 10 | null |
| first_aid_kit | First Aid Kit | 1 | 25 | null |
| antiseptic_bottle | Antiseptic Bottle | 1 | 12 | null |
| syringe_pack | Syringe Pack | 1 | 15 | null |
| suture_kit | Suture Kit | 1 | 30 | null |
| stethoscope | Stethoscope | 1 | 35 | null |
| blood_pressure_cuff | Blood Pressure Cuff | 1 | 28 | null |
| thermometer | Clinical Thermometer | 1 | 18 | null |
| prescription_pad | Prescription Pad | 1 | 40 | null |
| pill_bottle_opioid | Opioid Pill Bottle | 1 | 65 | null |
| pill_bottle_stimulant | Stimulant Bottle | 1 | 60 | null |
| vial_antibiotic | Antibiotic Vial | 1 | 45 | null |
| vial_vaccine | Vaccine Vial | 1 | 55 | null |
| insulin_pen | Insulin Pen | 1 | 50 | null |
| epipen | EpiPen | 1 | 40 | null |
| anesthetic_vial | Anesthetic Vial | 1 | 80 | null |
| morphine_ampoule | Morphine Ampoule | 1 | 95 | null |
| chemo_vial | Chemo Vial | 1 | 110 | null |
| rare_blood_bag | Rare Blood Bag | 2 | 120 | null |
| plasma_pack | Plasma Pack | 1 | 70 | null |
| surgical_tray | Surgical Tray | 2 | 90 | null |
| scalpel_set | Scalpel Set | 1 | 35 | null |
| orthopedic_drill | Orthopedic Drill | 2 | 100 | null |
| defibrillator | Defibrillator | 2 | 130 | null |
| ultrasound_probe | Ultrasound Probe | 1 | 85 | null |
| endoscope | Endoscope | 2 | 115 | null |
| lab_centrifuge | Lab Centrifuge | 2 | 140 | null |
| microscope_clinical | Clinical Microscope | 2 | 150 | null |
| pcr_machine | PCR Machine | 3 | 220 | null |
| gene_sequencer | Gene Sequencer | 3 | 320 | null |
| cryo_vial_rack | Cryo Vial Rack | 2 | 160 | null |
| tissue_sample_jar | Tissue Sample Jar | 1 | 75 | null |
| pathology_slide_box | Pathology Slide Box | 1 | 65 | null |
| blood_bag_rack | Blood Bag Rack | 2 | 95 | null |
| pharmacy_safe_tray | Pharmacy Safe Tray | 2 | 180 | null |
| controlled_substance_box | Controlled Substance Box | 2 | 200 | null |
| clinical_trial_binder | Clinical Trial Binder | 1 | 60 | null |
| patient_records_box | Patient Records Box | 2 | 85 | null |
| medical_waste_bin | Medical Waste Bin | 3 | 45 | null |

**Sets:**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| surgical_kit | 3 — `surgical_scalpel`, `surgical_forceps`, `surgical_retractor` | 150 | Instruments |
| trauma_kit | 3 — `trauma_tourniquet`, `trauma_dressing`, `trauma_shears` | 120 | Field kit |
| pharmacy_dispensary | 3 — `dispensary_scale`, `dispensary_mortar`, `dispensary_counting_tray` | 160 | Dispensary |
| diagnostic_set | 3 — `diag_stethoscope`, `diag_cuff`, `diag_otoscope` | 140 | Instruments |
| lab_starter_kit | 3 — `lab_beaker_set`, `lab_pipette_set`, `lab_burner` | 130 | Lab |
| vaccine_cold_chain | 3 — `cold_box`, `ice_pack_set`, `temp_logger` | 170 | Logistics |
| opioid_dispensary | 3 — `opioid_bottle_a`, `opioid_bottle_b`, `opioid_bottle_c` | 180 | Controlled |
| anesthesia_cart | 3 — `anesthesia_vial_set`, `anesthesia_mask`, `anesthesia_monitor` | 250 | Surgical |
| dental_suite | 3 — `dental_drill`, `dental_mirror_set`, `dental_mold_kit` | 145 | Dental |
| biohazard_sampling | 3 — `sample_container_set`, `swab_kit`, `seal_bag_set` | 110 | Lab |

**Set-Piece Individual Prices:**
- `surgical_kit`: 55 + 50 + 45 = 150
- `trauma_kit`: 50 + 40 + 30 = 120
- `pharmacy_dispensary`: 60 + 50 + 50 = 160
- `diagnostic_set`: 50 + 45 + 45 = 140
- `lab_starter_kit`: 45 + 45 + 40 = 130
- `vaccine_cold_chain`: 70 + 50 + 50 = 170
- `opioid_dispensary`: 60 + 60 + 60 = 180
- `anesthesia_cart`: 90 + 80 + 80 = 250
- `dental_suite`: 55 + 45 + 45 = 145
- `biohazard_sampling`: 40 + 35 + 35 = 110

**Main-score candidates**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_panacea | The Panacea | 1 | 420 | null | true |
| the_antidote | The Antidote | 1 | 380 | null | true |
| the_clinical_trial_data | The Clinical Trial Data | 1 | 350 | null | true |
| the_organ_container | The Organ Container | 2 | 450 | null | true |

## Clothing / Textiles / Fashion

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| cotton_bolt | Cotton Bolt | 2 | 15 | null |
| linen_bolt | Linen Bolt | 2 | 22 | null |
| wool_bolt | Wool Bolt | 2 | 28 | null |
| silk_bolt | Silk Bolt | 2 | 45 | null |
| velvet_bolt | Velvet Bolt | 2 | 55 | null |
| denim_roll | Denim Roll | 2 | 20 | null |
| leather_hide | Leather Hide | 2 | 35 | null |
| fur_pelt | Fur Pelt | 2 | 80 | null |
| lace_spool | Lace Spool | 1 | 30 | null |
| thread_box | Thread Box | 1 | 10 | null |
| button_tin | Button Tin | 1 | 8 | null |
| sewing_kit | Sewing Kit | 1 | 18 | null |
| scarf_silk | Silk Scarf | 1 | 35 | null |
| tie_designer | Designer Tie | 1 | 40 | null |
| gloves_leather | Leather Gloves | 1 | 45 | null |
| hat_felt | Felt Hat | 1 | 50 | null |
| shoes_designer | Designer Shoes | 1 | 90 | null |
| handbag_designer | Designer Handbag | 1 | 140 | null |
| belt_designer | Designer Belt | 1 | 60 | null |
| coat_fur | Fur Coat | 2 | 180 | null |
| coat_cashmere | Cashmere Coat | 2 | 120 | null |
| suit_tailored | Tailored Suit | 2 | 150 | null |
| dress_couture | Couture Dress | 1 | 200 | null |
| gown_evening | Evening Gown | 2 | 170 | null |
| uniform_security | Security Uniform | 1 | 30 | null |
| uniform_staff | Staff Uniform | 1 | 20 | null |
| costume_theater | Theater Costume | 2 | 65 | null |
| wedding_dress | Wedding Dress | 2 | 220 | null |
| vest_kevlar | Kevlar Vest | 1 | 110 | null |
| apron_butcher | Butcher's Apron | 1 | 12 | null |
| bolt_end_lot | Bolt-End Lot | 3 | 40 | null |
| textile_bale | Textile Bale | 3 | 60 | null |

**Sets:**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_tailors_kit | 3 — `tailor_shears`, `tailor_pins`, `tailor_tape` | 90 | Tools |
| the_gentlemans_wardrobe | 3 — `wardrobe_suit`, `wardrobe_shoes`, `wardrobe_tie` | 280 | Formalwear |
| the_evening_ensemble | 3 — `ensemble_gown`, `ensemble_clutch`, `ensemble_shoes` | 330 | Formalwear |
| the_bridal_trousseau | 3 — `trousseau_dress`, `trousseau_veil`, `trousseau_shoes` | 360 | Bridal |
| the_fur_collection | 3 — `fur_coat_a`, `fur_coat_b`, `fur_stole` | 380 | Luxury |
| the_designer_display | 3 — `display_handbag`, `display_shoes`, `display_belt` | 260 | Boutique |
| the_linen_chest | 3 — `linen_sheet_set`, `linen_towel_set`, `linen_tablecloth` | 120 | Household |
| the_military_uniform | 3 — `uniform_jacket`, `uniform_trousers`, `uniform_cap` | 160 | Uniform |
| the_theater_wardrobe | 3 — `costume_hero`, `costume_villain`, `costume_extra` | 200 | Costume |
| the_silk_route | 3 — `silk_bolt_a`, `silk_bolt_b`, `silk_bolt_c` | 150 | Textile |

**Set-Piece Individual Prices:**
- `the_tailors_kit`: 30 + 30 + 30 = 90
- `the_gentlemans_wardrobe`: 150 + 90 + 40 = 280
- `the_evening_ensemble`: 170 + 100 + 60 = 330
- `the_bridal_trousseau`: 220 + 80 + 60 = 360
- `the_fur_collection`: 180 + 150 + 50 = 380
- `the_designer_display`: 140 + 90 + 30 = 260
- `the_linen_chest`: 50 + 40 + 30 = 120
- `the_military_uniform`: 70 + 50 + 40 = 160
- `the_theater_wardrobe`: 75 + 70 + 55 = 200
- `the_silk_route`: 50 + 50 + 50 = 150

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_designer_original | The Designer Original | 1 | 380 | null | true |
| the_royal_gown | The Royal Gown | 2 | 420 | null | true |
| the_lost_tapestry | The Lost Tapestry | 3 | 400 | null | true |
| the_master_tailors_commission | The Master Tailor's Commission | 1 | 320 | null | true |

## Tools / Trade Goods

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| hammer | Hammer | 1 | 10 | null |
| screwdriver_set | Screwdriver Set | 1 | 12 | null |
| wrench_set | Wrench Set | 1 | 25 | null |
| pliers | Pliers | 1 | 8 | null |
| crowbar | Crowbar | 1 | 15 | null |
| hacksaw | Hacksaw | 1 | 14 | null |
| bolt_cutters | Bolt Cutters | 1 | 35 | null |
| lockpick_kit | Lockpick Kit | 1 | 60 | null |
| pry_bar | Pry Bar | 2 | 30 | null |
| sledgehammer | Sledgehammer | 2 | 28 | null |
| tool_belt | Tool Belt | 1 | 20 | null |
| tool_box | Toolbox | 2 | 38 | null |
| bag_of_tools | Bag of Tools | 1 | 25 | null |
| drill_power | Power Drill | 2 | 55 | null |
| angle_grinder | Angle Grinder | 2 | 70 | null |
| circular_saw | Circular Saw | 2 | 80 | null |
| reciprocating_saw | Reciprocating Saw | 2 | 65 | null |
| impact_driver | Impact Driver | 1 | 50 | null |
| nail_gun | Nail Gun | 2 | 75 | null |
| heat_gun | Heat Gun | 1 | 40 | null |
| welding_torch | Welding Torch | 2 | 90 | null |
| multimeter | Multimeter | 1 | 45 | null |
| voltage_tester | Voltage Tester | 1 | 20 | null |
| pipe_cutter | Pipe Cutter | 1 | 30 | null |
| plumbing_kit | Plumbing Kit | 2 | 60 | null |
| electrical_kit | Electrical Kit | 2 | 70 | null |
| carpentry_kit | Carpentry Kit | 2 | 65 | null |
| masonry_trowel | Masonry Trowel | 1 | 12 | null |
| surveyor_level | Surveyor's Level | 2 | 120 | null |
| laser_level | Laser Level | 1 | 85 | null |
| caliper_precision | Precision Caliper | 1 | 55 | null |
| micrometer | Micrometer | 1 | 50 | null |
| tap_and_die_set | Tap and Die Set | 2 | 95 | null |
| machine_vise | Machine Vise | 3 | 110 | null |
| bench_grinder | Bench Grinder | 3 | 100 | null |
| drill_press | Drill Press | 3 | 180 | null |
| lathe_benchtop | Benchtop Lathe | 3 | 240 | null |
| mill_benchtop | Benchtop Mill | 3 | 280 | null |
| welding_rig | Welding Rig | 3 | 220 | null |
| air_compressor | Air Compressor | 3 | 150 | null |
| generator_portable | Portable Generator | 3 | 170 | null |
| heavy_machinery_part | Heavy Machinery Part | 3 | 80 | null |
| hydraulic_pump | Hydraulic Pump | 2 | 130 | null |
| bearing_set | Bearing Set | 1 | 40 | null |
| gear_set | Gear Set | 1 | 45 | null |
| motor_electric | Electric Motor | 2 | 95 | null |
| pump_industrial | Industrial Pump | 3 | 160 | null |
| valve_set | Valve Set | 1 | 35 | null |
| pipe_fitting_lot | Pipe Fitting Lot | 2 | 30 | null |
| fastener_lot | Fastener Lot | 1 | 15 | null |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_mechanics_kit | 3 — `mech_wrench_set`, `mech_socket_set`, `mech_screwdriver_set` | 130 | Automotive |
| the_electricians_kit | 3 — `elec_multimeter`, `elec_tester`, `elec_wire_stripper` | 120 | Electrical |
| the_plumbers_kit | 3 — `plumb_pipe_cutter`, `plumb_wrench`, `plumb_snake` | 125 | Plumbing |
| the_carpenters_kit | 3 — `carp_saw`, `carp_chisel_set`, `carp_square` | 140 | Carpentry |
| the_machinists_kit | 3 — `mach_caliper`, `mach_micrometer`, `mach_gauge_block` | 180 | Precision |
| the_welders_rig | 3 — `weld_torch`, `weld_mask`, `weld_gloves` | 200 | Welding |
| the_lockpicks | 3 — `pick_rake`, `pick_tension`, `pick_decoder` | 150 | Bypass |
| the_demolition_kit | 3 — `demo_pry_bar`, `demo_sledge`, `demo_bolt_cutter` | 130 | Breaching |
| the_gardeners_kit | 3 — `gard_shears`, `gard_trowel`, `gard_hose_reel` | 90 | Grounds |
| the_industrial_spares | 3 — `spare_bearing`, `spare_gear`, `spare_motor` | 220 | Industrial |

**Set-piece individual prices:**
- `the_mechanics_kit`: 45 + 45 + 40 = 130
- `the_electricians_kit`: 45 + 35 + 40 = 120
- `the_plumbers_kit`: 40 + 45 + 40 = 125
- `the_carpenters_kit`: 50 + 50 + 40 = 140
- `the_machinists_kit`: 60 + 60 + 60 = 180
- `the_welders_rig`: 90 + 60 + 50 = 200
- `the_lockpicks`: 50 + 50 + 50 = 150
- `the_demolition_kit`: 45 + 45 + 40 = 130
- `the_gardeners_kit`: 30 + 30 + 30 = 90
- `the_industrial_spares`: 75 + 70 + 75 = 220

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_master_toolkit | The Master Toolkit | 3 | 380 | null | true |
| the_prototype_lathe | The Prototype Lathe | 3 | 420 | null | true |
| the_master_key_set | The Master Key Set | 1 | 400 | null | true |
| the_trade_secret | The Trade Secret | 1 | 300 | null | true |

## Vehicle Parts

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| tire | Tire | 2 | 25 | null |
| tire_set | Tire Set | 3 | 90 | null |
| wheel_rim | Wheel Rim | 2 | 40 | null |
| rim_set | Rim Set | 3 | 140 | null |
| car_battery | Car Battery | 2 | 45 | null |
| alternator | Alternator | 2 | 60 | null |
| starter_motor | Starter Motor | 2 | 55 | null |
| brake_pad_set | Brake Pad Set | 1 | 30 | null |
| brake_caliper | Brake Caliper | 2 | 50 | null |
| shock_absorber | Shock Absorber | 2 | 40 | null |
| suspension_kit | Suspension Kit | 3 | 120 | null |
| exhaust_system | Exhaust System | 3 | 95 | null |
| catalytic_converter | Catalytic Converter | 2 | 110 | null |
| radiator | Radiator | 2 | 65 | null |
| fuel_pump | Fuel Pump | 2 | 70 | null |
| fuel_injector_set | Fuel Injector Set | 1 | 85 | null |
| carburetor | Carburetor | 2 | 75 | null |
| turbocharger | Turbocharger | 2 | 160 | null |
| supercharger | Supercharger | 2 | 180 | null |
| ecu_unit | ECU Unit | 1 | 130 | null |
| wiring_harness | Wiring Harness | 2 | 60 | null |
| headlight_assembly | Headlight Assembly | 1 | 35 | null |
| taillight_assembly | Taillight Assembly | 1 | 30 | null |
| side_mirror | Side Mirror | 1 | 20 | null |
| bumper | Bumper | 3 | 45 | null |
| hood_panel | Hood Panel | 3 | 55 | null |
| door_panel | Door Panel | 3 | 50 | null |
| windshield | Windshield | 3 | 70 | null |
| seat_leather | Leather Seat | 2 | 80 | null |
| seat_set | Seat Set | 3 | 150 | null |
| dashboard_console | Dashboard Console | 3 | 100 | null |
| steering_wheel | Steering Wheel | 1 | 40 | null |
| gearbox | Gearbox | 3 | 220 | null |
| transmission | Transmission | 3 | 280 | null |
| engine_block | Engine Block | 3 | 320 | null |
| cylinder_head | Cylinder Head | 3 | 200 | null |
| crankshaft | Crankshaft | 3 | 180 | null |
| motorcycle_engine | Motorcycle Engine | 2 | 210 | null |
| motorcycle_frame | Motorcycle Frame | 3 | 150 | null |
| marine_engine | Marine Engine | 3 | 340 | null |
| tractor_part | Tractor Part | 3 | 130 | null |
| forklift_part | Forklift Part | 3 | 110 | null |
| ev_battery_pack | EV Battery Pack | 3 | 380 | null |
| ev_motor | EV Motor | 3 | 260 | null |
| ev_charger | EV Charger | 2 | 140 | null |
| obd_scanner | OBD Scanner | 1 | 50 | null |
| tool_cart_auto | Auto Tool Cart | 3 | 160 | null |
| diagnostic_laptop | Diagnostic Laptop | 1 | 120 | null |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_tune_up_kit | 3 — `tune_spark_plug_set`, `tune_oil_filter`, `tune_air_filter` | 90 | Maintenance |
| the_brake_job | 3 — `brake_pads`, `brake_rotors`, `brake_caliper` | 140 | Brakes |
| the_suspension_kit | 3 — `susp_shock`, `susp_spring`, `susp_strut` | 160 | Suspension |
| the_engine_rebuild | 3 — `rebuild_piston_set`, `rebuild_ring_set`, `rebuild_bearing_set` | 200 | Engine |
| the_turbo_kit | 3 — `turbo_unit`, `turbo_intercooler`, `turbo_wastegate` | 260 | Performance |
| the_ev_drivetrain | 3 — `ev_motor`, `ev_inverter`, `ev_reduction_gear` | 340 | EV |
| the_mechanic_cart | 3 — `cart_wrench_set`, `cart_socket_set`, `cart_diagnostic` | 180 | Tools |
| the_motorcycle_build | 3 — `moto_engine`, `moto_frame`, `moto_wheel_set` | 320 | Motorcycle |
| the_marine_kit | 3 — `marine_prop`, `marine_pump`, `marine_battery` | 220 | Marine |
| the_detailing_kit | 3 — `detail_polish`, `detail_vacuum`, `detail_steamer` | 110 | Detailing |

**Set-piece individual prices:**
- `the_tune_up_kit`: 30 + 30 + 30 = 90
- `the_brake_job`: 30 + 50 + 60 = 140
- `the_suspension_kit`: 50 + 55 + 55 = 160
- `the_engine_rebuild`: 70 + 65 + 65 = 200
- `the_turbo_kit`: 100 + 80 + 80 = 260
- `the_ev_drivetrain`: 120 + 110 + 110 = 340
- `the_mechanic_cart`: 60 + 60 + 60 = 180
- `the_motorcycle_build`: 120 + 100 + 100 = 320
- `the_marine_kit`: 80 + 70 + 70 = 220
- `the_detailing_kit`: 40 + 35 + 35 = 110

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_concept_car | The Concept Car | 3 | 500 | null | true |
| the_getaway_vehicle | The Getaway Vehicle | 2 | 450 | null | true |
| the_race_engine | The Race Engine | 2 | 420 | null | true |
| the_prototype_ev | The Prototype EV | 3 | 480 | null | true |
| the_armored_transport | The Armored Transport | 3 | 450 | null | true |

## Collectibles / Memorabilia

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| trading_card_pack | Trading Card Pack | 1 | 8 | null |
| trading_card_single | Trading Card (Single) | 1 | 15 | null |
| trading_card_holo | Holo Trading Card | 1 | 45 | null |
| trading_card_graded | Graded Trading Card | 1 | 120 | null |
| card_binder | Card Binder | 1 | 30 | null |
| card_booster_box | Booster Box (Sealed) | 2 | 90 | null |
| stamp_single | Stamp (Single) | 1 | 10 | null |
| stamp_sheet | Stamp Sheet | 1 | 25 | null |
| stamp_album | Stamp Album | 2 | 70 | null |
| stamp_rare | Rare Stamp | 1 | 110 | null |
| coin_single | Coin (Single) | 1 | 12 | null |
| coin_set_boxed | Boxed Coin Set | 1 | 40 | null |
| coin_rare | Rare Coin | 1 | 130 | null |
| coin_album | Coin Album | 2 | 85 | null |
| comic_single | Comic Book (Single) | 1 | 15 | null |
| comic_key_issue | Key Issue Comic | 1 | 90 | null |
| comic_graded | Graded Comic | 1 | 150 | null |
| comic_long_box | Long Box of Comics | 2 | 60 | null |
| comic_short_box | Short Box of Comics | 2 | 35 | null |
| vinyl_record | Vinyl Record | 1 | 12 | null |
| vinyl_rare_press | Rare Pressing | 1 | 70 | null |
| vinyl_box_lot | Box of Vinyl | 3 | 50 | null |
| vinyl_sealed | Sealed Vinyl | 1 | 55 | null |
| action_figure | Action Figure | 1 | 18 | null |
| action_figure_boxed | Boxed Action Figure | 1 | 45 | null |
| action_figure_rare | Rare Action Figure | 1 | 110 | null |
| toy_diecast | Die-Cast Toy | 1 | 14 | null |
| toy_diecast_rare | Rare Die-Cast | 1 | 65 | null |
| toy_model_kit | Model Kit | 2 | 30 | null |
| sports_card_single | Sports Card | 1 | 15 | null |
| sports_card_rookie | Rookie Card | 1 | 100 | null |
| sports_card_graded | Graded Sports Card | 1 | 160 | null |
| signed_ball | Signed Ball | 1 | 55 | null |
| signed_jersey | Signed Jersey | 2 | 130 | null |
| autograph_photo | Autograph Photo | 1 | 40 | null |
| autograph_book | Signed Book | 1 | 60 | null |
| program_vintage | Vintage Program | 1 | 25 | null |
| ticket_stub_vintage | Vintage Ticket Stub | 1 | 20 | null |
| poster_vintage | Vintage Poster | 2 | 75 | null |
| poster_movie | Movie Poster | 2 | 65 | null |
| lobby_card | Lobby Card | 1 | 35 | null |
| prop_replica | Prop Replica | 2 | 80 | null |
| memorabilia_helmet | Signed Helmet | 2 | 120 | null |
| memorabilia_glove | Signed Glove | 1 | 85 | null |
| cereal_box_vintage | Vintage Cereal Box | 2 | 30 | null |
| lunchbox_vintage | Vintage Lunchbox | 1 | 35 | null |
| figure_statue_limited | Limited Statue | 2 | 140 | null |
| pez_dispenser | Vintage Pez Dispenser | 1 | 22 | null |
| board_game_vintage | Vintage Board Game | 2 | 45 | null |
| puzzle_boxed | Boxed Puzzle | 2 | 18 | null |
| storage_unit_box | Storage Unit Box (Unsearched) | 2 | 40 | null |
| storage_unit_lot | Storage Unit Lot | 3 | 120 | null |
| ephemera_folder | Ephemera Folder | 1 | 25 | null |
| matchbook_collection | Matchbook Collection | 1 | 15 | null |
| postcard_lot | Postcard Lot | 1 | 18 | null |
| banknote_collection | Banknote Collection | 1 | 95 | null |
| militaria_medal | Militaria Medal | 1 | 70 | null |
| militaria_patch_set | Militaria Patch Set | 1 | 30 | null |
| fossil_ammonite | Ammonite Fossil | 1 | 45 | null |
| mineral_specimen | Mineral Specimen | 1 | 35 | null |
| taxidermy_small | Small Taxidermy | 2 | 55 | null |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_card_collection | 3 — `card_charizard`, `card_black_lotus`, `card_rookie` | 300 | Trading cards |
| the_stamp_album_set | 3 — `stamp_penny_black`, `stamp_inverted`, `stamp_blue_mauritius` | 320 | Stamps |
| the_coin_collection | 3 — `coin_double_eagle`, `coin_denarius`, `coin_morgan` | 280 | Coins |
| the_comic_run | 3 — `comic_issue_1`, `comic_issue_2`, `comic_issue_3` | 260 | Comics |
| the_vinyl_crate | 3 — `vinyl_first_press`, `vinyl_promo`, `vinyl_test_press` | 240 | Vinyl |
| the_sports_memorabilia | 3 — `sports_rookie_card`, `sports_signed_ball`, `sports_signed_jersey` | 340 | Sports |
| the_autograph_book | 3 — `autograph_celeb_a`, `autograph_celeb_b`, `autograph_celeb_c` | 220 | Autographs |
| the_movie_memorabilia | 3 — `movie_poster_original`, `movie_lobby_set`, `movie_prop` | 280 | Film |
| the_toy_collection | 3 — `toy_boxed_figure`, `toy_rare_variant`, `toy_vehicle` | 200 | Toys |
| the_militaria_set | 3 — `militaria_medal_set`, `militaria_patch_set`, `militaria_insignia` | 260 | Militaria |
| the_natural_history | 3 — `fossil_ammonite`, `fossil_trilobite`, `fossil_fish` | 180 | Natural history |
| the_storage_find | 3 — `storage_box_a`, `storage_box_b`, `storage_box_c` | 150 | Storage lot |

**Set-piece individual prices:**
- `the_card_collection`: 120 + 100 + 80 = 300
- `the_stamp_album_set`: 120 + 100 + 100 = 320
- `the_coin_collection`: 100 + 90 + 90 = 280
- `the_comic_run`: 90 + 85 + 85 = 260
- `the_vinyl_crate`: 80 + 80 + 80 = 240
- `the_sports_memorabilia`: 100 + 110 + 130 = 340
- `the_autograph_book`: 70 + 75 + 75 = 220
- `the_movie_memorabilia`: 110 + 90 + 80 = 280
- `the_toy_collection`: 75 + 65 + 60 = 200
- `the_militaria_set`: 90 + 85 + 85 = 260
- `the_natural_history`: 60 + 60 + 60 = 180
- `the_storage_find`: 50 + 50 + 50 = 150

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_mint_condition_grail | The Mint-Condition Grail | 1 | 420 | null | true |
| the_complete_collection | The Complete Collection | 3 | 450 | null | true |
| the_lost_album | The Lost Album | 2 | 380 | null | true |
| the_auction_centerpiece | The Auction Centerpiece | 1 | 400 | null | true |

## Office / Commercial Supplies

| id | name | grabCost | pieceValue | set |
|---|---|---|---|---|
| stapler | Stapler | 1 | 5 | null |
| tape_dispenser | Tape Dispenser | 1 | 6 | null |
| hole_punch | Hole Punch | 1 | 7 | null |
| scissors_office | Office Scissors | 1 | 8 | null |
| pen_box | Box of Pens | 1 | 6 | null |
| notebook_stack | Notebook Stack | 1 | 10 | null |
| sticky_note_pack | Sticky Note Pack | 1 | 4 | null |
| binder_lot | Binder Lot | 2 | 15 | null |
| paper_ream | Paper Ream | 2 | 8 | null |
| envelope_box | Envelope Box | 1 | 7 | null |
| stamp_roll | Postage Stamp Roll | 1 | 20 | null |
| calculator | Calculator | 1 | 12 | null |
| label_maker | Label Maker | 1 | 18 | null |
| shredder | Paper Shredder | 2 | 35 | null |
| laminator | Laminator | 2 | 30 | null |
| binding_machine | Binding Machine | 2 | 45 | null |
| desk_phone | Desk Phone | 1 | 20 | null |
| conference_phone | Conference Phone | 2 | 60 | null |
| printer_desktop | Desktop Printer | 2 | 40 | null |
| printer_commercial | Commercial Printer | 3 | 120 | null |
| copier | Copier | 3 | 150 | null |
| scanner_office | Office Scanner | 2 | 55 | null |
| fax_machine | Fax Machine | 2 | 25 | null |
| projector | Projector | 2 | 70 | null |
| whiteboard | Whiteboard | 2 | 25 | null |
| desk_chair | Desk Chair | 2 | 30 | null |
| office_desk | Office Desk | 3 | 45 | null |
| filing_cabinet | Filing Cabinet | 3 | 50 | null |
| safe_office | Office Safe | 3 | 160 | null |
| lockbox_office | Office Lockbox | 2 | 70 | null |
| cash_register | Cash Register | 3 | 110 | null |
| pos_terminal | POS Terminal | 2 | 80 | null |
| card_reader | Card Reader | 1 | 35 | null |
| receipt_printer | Receipt Printer | 1 | 25 | null |
| barcode_scanner | Barcode Scanner | 1 | 30 | null |
| time_clock | Time Clock | 2 | 55 | null |
| coffee_machine | Coffee Machine | 2 | 40 | null |
| water_cooler | Water Cooler | 3 | 25 | null |
| microwave_office | Office Microwave | 2 | 20 | null |
| mini_fridge | Mini Fridge | 3 | 30 | null |
| vending_machine | Vending Machine | 3 | 90 | null |
| atm_desktop | Desktop ATM | 3 | 180 | null |
| key_cabinet | Key Cabinet | 2 | 65 | null |
| badge_printer | Badge Printer | 2 | 85 | null |
| access_control_panel | Access Control Panel | 2 | 95 | null |
| server_rack_small | Small Server Rack | 3 | 140 | null |
| network_switch | Network Switch | 1 | 45 | null |
| ups_battery | UPS Battery | 2 | 50 | null |
| cable_lot | Cable Lot | 2 | 15 | null |
| toner_cartridge | Toner Cartridge | 1 | 22 | null |
| toner_cartridge_lot | Toner Cartridge Lot | 2 | 60 | null |
| ledgers_crate | Ledger Crate | 2 | 42 | null |
| stock_manifest | Stock Manifest | 1 | 28 | null |
| delivery_schedule | Delivery Schedule | 1 | 20 | null |
| mail_cart | Mail Cart | 3 | 35 | null |
| package_lot | Package Lot | 2 | 30 | null |
| uniform_rack | Uniform Rack | 3 | 40 | null |
| office_keys | Office Keys | 1 | 50 | null |
| master_keycard | Master Keycard | 1 | 90 | null |
| staff_badge | Staff Badge | 1 | 15 | null |

**Sets**

| Set id | Pieces | Total piece value | Category |
|---|---|---|---|
| the_desk_starter | 3 — `desk_stapler`, `desk_tape`, `desk_pen_set` | 45 | Desk supplies |
| the_printer_station | 3 — `printer_unit`, `printer_toner`, `printer_paper` | 90 | Machines |
| the_conference_kit | 3 — `conf_phone`, `conf_projector`, `conf_whiteboard` | 130 | Meeting room |
| the_cash_handling | 3 — `cash_register`, `cash_reader`, `cash_receipt_printer` | 150 | Retail |
| the_security_desk | 3 — `sec_badge_printer`, `sec_access_panel`, `sec_key_cabinet` | 220 | Security |
| the_it_closet | 3 — `it_switch`, `it_ups`, `it_cable_lot` | 110 | IT |
| the_breakroom | 3 — `break_coffee`, `break_microwave`, `break_fridge` | 80 | Breakroom |
| the_filing_system | 3 — `file_cabinet`, `file_binder_set`, `file_label_set` | 95 | Filing |
| the_office_safe_kit | 3 — `safe_lockbox`, `safe_ledger`, `safe_key_set` | 180 | Valuables |
| the_retail_floor | 3 — `retail_scanner`, `retail_pos`, `retail_time_clock` | 140 | Retail |

**Set-Piece Individual Prices:**
- `the_desk_starter`: 15 + 15 + 15 = 45
- `the_printer_station`: 40 + 30 + 20 = 90
- `the_conference_kit`: 60 + 50 + 20 = 130
- `the_cash_handling`: 110 + 20 + 20 = 150
- `the_security_desk`: 85 + 75 + 60 = 220
- `the_it_closet`: 45 + 50 + 15 = 110
- `the_breakroom`: 40 + 20 + 20 = 80
- `the_filing_system`: 50 + 25 + 20 = 95
- `the_office_safe_kit`: 70 + 60 + 50 = 180
- `the_retail_floor`: 30 + 80 + 30 = 140

**Main-score candidates (designated per target, not sold as sets)**

| id | name | grabCost | pieceValue | set | isMainScore |
|---|---|---|---|---|---|
| the_company_ledger | The Company Ledger | 1 | 280 | null | true |
| the_blackmail_files | The Blackmail Files | 1 | 350 | null | true |
| the_golden_handshake | The Golden Handshake | 2 | 300 | null | true |
| the_corporate_secrets | The Corporate Secrets | 2 | 400 | null | true |

















































