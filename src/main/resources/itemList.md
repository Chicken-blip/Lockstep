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


















